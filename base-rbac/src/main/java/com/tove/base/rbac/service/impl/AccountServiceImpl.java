package com.tove.base.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tove.base.rbac.dao.*;
import com.tove.base.rbac.model.*;
import com.tove.base.rbac.service.AccountService;
import com.tove.infra.common.BaseErrorCode;
import com.tove.infra.common.BaseException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private CacheService cacheService;

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private PowerMapper powerMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserGroupMapper userGroupMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RelateGroupRoleMapper relateGroupRoleMapper;

    @Resource
    private RelateGroupUserMapper relateGroupUserMapper;

    @Resource
    private RelateRolePowerMapper relateRolePowerMapper;

    @Resource
    private RelateUserRoleMapper relateUserRoleMapper;

    @Override
    public List<Power> getUserPowers(Long uid) {
        List<Long> roleIdList = getUserRoles(uid).stream().map(Role::getId).collect(Collectors.toList());

        List<Power> powerList = cacheService.getUserPowerCache().getIfPresent(uid);
        if (powerList != null){
            return powerList;
        }
        powerList = accountMapper.getPowersByRoles(roleIdList);
        cacheService.getUserPowerCache().put(uid, powerList);
        return powerList;
    }

    @Override
    public List<Role> getUserRoles(Long uid) {
        List<Role> roleList = cacheService.getUserRoleCache().getIfPresent(uid);
        if (roleList != null){
            return roleList;
        }

        List<Role> rolesFromUser = accountMapper.getUserRoles(uid);
        List<Long> groupIds = getUserGroups(uid)
                .stream()
                .map(UserGroup::getId)
                .collect(Collectors.toList());
        // 这里并非去找缓存中所有的权限
        List<Role> rolesFromGroup = accountMapper.getUserGroupRoles(groupIds);

        Set<Role> roleSet = new HashSet<>(rolesFromUser);
        roleSet.addAll(rolesFromGroup);
        roleList = new ArrayList<>(roleSet);
        return roleList;
    }

    @Override
    public List<UserGroup> getUserGroups(Long uid){
        List<UserGroup> userGroupList = cacheService.getUserGroupCache().getIfPresent(uid);
        if (userGroupList != null){
            return userGroupList;
        }
        userGroupList = accountMapper.getUserGroups(uid);
        cacheService.getUserGroupCache().put(uid, userGroupList);
        return userGroupList;
    }

    @Override
    public List<User> getUserInGroup(Long ugid) {
        List<User> result = cacheService.getUserInGroupCache().getIfPresent(ugid);
        if (result != null){
            return result;
        }
        result = accountMapper.getUserInGroup(ugid);
        cacheService.getUserInGroupCache().put(ugid, result);
        return result;
    }

    @Override
    public List<Power> getPowerInRole(Long rid) {
        List<Power> result = cacheService.getPowerInRoleCache().getIfPresent(rid);
        if (result != null){
            return result;
        }
        result = accountMapper.getPowerInRole(rid);
        cacheService.getPowerInRoleCache().put(rid, result);
        return result;
    }

    @Override
    public List<Role> getRoleInGroup(Long ugid) {
        List<Role> result= cacheService.getRoleInGroupCache().getIfPresent(ugid);
        if (result != null){
            return result;
        }
        result = accountMapper.getRoleInGroup(ugid);
        cacheService.getRoleInGroupCache().put(ugid, result);
        return result;
    }

    @Override
    public boolean addRolePower(Long rid, Long pid) {
        RelateRolePower relateRolePower = new RelateRolePower();
        relateRolePower.setPid(pid);
        relateRolePower.setRid(rid);
        int rows = relateRolePowerMapper.insert(relateRolePower);
        return rows>0;
    }

    @Override
    public boolean deleteRolePower(Long rid, Long pid) {
        QueryWrapper<RelateRolePower> wrapper = new QueryWrapper<>();
        wrapper.eq("rid", rid);
        wrapper.eq("pid", pid);
        int rows = relateRolePowerMapper.delete(wrapper);
        // TODO 大面积失效
        return rows>0;
    }

    @Override
    public boolean addGroupRole(Long ugid, Long rid) {
        RelateGroupRole relateGroupRole = new RelateGroupRole();
        relateGroupRole.setRid(rid);
        relateGroupRole.setUgId(ugid);
        int rows = relateGroupRoleMapper.insert(relateGroupRole);
        return rows>0;
    }

    @Override
    public boolean deleteGroupRole(Long ugid, Long rid) {
        QueryWrapper<RelateGroupRole> wrapper = new QueryWrapper<>();
        wrapper.eq("rid", rid);
        wrapper.eq("ug_id", ugid);
        int rows = relateGroupRoleMapper.delete(wrapper);
        // TODO 大面积失效

        return rows>0;
    }

    @Override
    public boolean addUserGroup(Long uid, Long ugid) {
        RelateGroupUser relateGroupUser = new RelateGroupUser();
        relateGroupUser.setUid(uid);
        relateGroupUser.setUgId(ugid);
        int rows = relateGroupUserMapper.insert(relateGroupUser);
        return rows>0;
    }

    @Override
    public boolean deleteUserGroup(Long uid, Long ugid) {
        QueryWrapper<RelateGroupUser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        wrapper.eq("ug_id", ugid);
        int rows = relateGroupUserMapper.delete(wrapper);
        cacheService.getUserGroupCache().invalidate(uid);
        return rows>0;
    }

    @Override
    public boolean addUserRole(Long uid, Long rid) {
        RelateUserRole relateUserRole = new RelateUserRole();
        relateUserRole.setRid(rid);
        relateUserRole.setUid(uid);
        int rows = relateUserRoleMapper.insert(relateUserRole);
        return rows>0;
    }

    @Override
    public boolean deleteUserRole(Long uid, Long rid) {
        QueryWrapper<RelateUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        wrapper.eq("rid", rid);
        int rows = relateUserRoleMapper.delete(wrapper);
        // TODO 延时双删
        cacheService.getUserRoleCache().invalidate(uid);
        return rows>0;
    }

    @Override
    public long addUser(String name) {
        User user = new User();
        user.setUsername(name);
        try {
            userMapper.insert(user);
        }catch (DuplicateKeyException e){
            throw new BaseException(BaseErrorCode.DUPLICATE_KEY);
        }
        return user.getId();
    }

    @Override
    public long addGroup(String name) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        try {
            userGroupMapper.insert(userGroup);
        }catch (DuplicateKeyException e){
            throw new BaseException(BaseErrorCode.DUPLICATE_KEY);
        }
        return userGroup.getId();
    }

    @Override
    public long addRole(String name) {
        Role role = new Role();
        role.setName(name);
        try {
            roleMapper.insert(role);
        }catch (DuplicateKeyException e){
            throw new BaseException(BaseErrorCode.DUPLICATE_KEY);
        }
        return role.getId();
    }

    @Override
    public long addPower(String name) {
        Power power = new Power();
        power.setName(name);
        try {
            powerMapper.insert(power);
        }catch (DuplicateKeyException e){
            throw new BaseException(BaseErrorCode.DUPLICATE_KEY);
        }
        return power.getId();
    }

    @Override
    public boolean deleteUser(Long uid) {
        int rows  = userMapper.deleteById(uid);
        return rows>0;
    }

    @Override
    public boolean deleteGroup(Long ugid) {
        int rows = userGroupMapper.deleteById(ugid);
        return rows>0;
    }

    @Override
    public boolean deleteRole(Long rid) {
        int rows = roleMapper.deleteById(rid);
        return rows>0;
    }

    @Override
    public boolean deletePower(Long pid) {
        int rows = powerMapper.deleteById(pid);
        return rows>0;
    }

}
