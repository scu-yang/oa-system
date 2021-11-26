package com.tove.base.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tove.base.rbac.dao.*;
import com.tove.base.rbac.model.*;
import com.tove.base.rbac.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        cacheService.clearAllCache();
        return rows>0;
    }

    @Override
    public boolean deleteRolePower(Long rid, Long pid) {
        QueryWrapper<RelateRolePower> wrapper = new QueryWrapper<>();
        wrapper.eq("rid", rid);
        wrapper.eq("pid", pid);
        int rows = relateRolePowerMapper.delete(wrapper);
        // TODO 大面积失效
        cacheService.clearAllCache();
        return rows>0;
    }

    @Override
    public boolean addGroupRole(Long ugid, Long rid) {
        RelateGroupRole relateGroupRole = new RelateGroupRole();
        relateGroupRole.setRid(rid);
        relateGroupRole.setUgId(ugid);
        int rows = relateGroupRoleMapper.insert(relateGroupRole);

        // 删除这个组里面所有用户的缓存
        List<User> userList = getUserInGroup(ugid);
        for(User user: userList){
            cacheService.clearUserAllCache(user.getId());
        }

        return rows>0;
    }

    @Override
    public boolean deleteGroupRole(Long ugid, Long rid) {
        QueryWrapper<RelateGroupRole> wrapper = new QueryWrapper<>();
        wrapper.eq("rid", rid);
        wrapper.eq("ug_id", ugid);

        // 删除这个组里面所有用户的缓存
        List<User> userList = getUserInGroup(ugid);
        for(User user: userList){
            cacheService.clearUserAllCache(user.getId());
        }
        int rows = relateGroupRoleMapper.delete(wrapper);

        return rows>0;
    }

    @Override
    public boolean addUserGroup(Long uid, Long ugid) {
        RelateGroupUser relateGroupUser = new RelateGroupUser();
        relateGroupUser.setUid(uid);
        relateGroupUser.setUgId(ugid);
        int rows = relateGroupUserMapper.insert(relateGroupUser);
        cacheService.clearUserAllCache(uid);
        cacheService.getUserInGroupCache().invalidate(ugid);
        return rows>0;
    }

    @Override
    public boolean deleteUserGroup(Long uid, Long ugid) {
        QueryWrapper<RelateGroupUser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        wrapper.eq("ug_id", ugid);
        int rows = relateGroupUserMapper.delete(wrapper);
        cacheService.clearUserAllCache(uid);
        cacheService.getUserInGroupCache().invalidate(ugid);
        return rows>0;
    }

    @Override
    public boolean addUserRole(Long uid, Long rid) {
        RelateUserRole relateUserRole = new RelateUserRole();
        relateUserRole.setRid(rid);
        relateUserRole.setUid(uid);
        int rows = relateUserRoleMapper.insert(relateUserRole);
        cacheService.getUserRoleCache().invalidate(uid);
        cacheService.getUserPowerCache().invalidate(uid);
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
        cacheService.getUserPowerCache().invalidate(uid);
        return rows>0;
    }

    @Override
    public long addUser(String name) {
        User user = new User();
        user.setUsername(name);
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public long addGroup(String name) {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroupMapper.insert(userGroup);
        return userGroup.getId();
    }

    @Override
    public long addRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleMapper.insert(role);
        return role.getId();
    }

    @Override
    public long addPower(String name) {
        Power power = new Power();
        power.setName(name);
        powerMapper.insert(power);
        return power.getId();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long uid) {
        int rows  = userMapper.deleteById(uid);

        QueryWrapper<RelateGroupUser> wrapperGroup = new QueryWrapper<>();
        wrapperGroup.eq("uid",uid);
        relateGroupUserMapper.delete(wrapperGroup);

        QueryWrapper<RelateUserRole> wrapperRole = new QueryWrapper<>();
        wrapperRole.eq("uid", uid);
        relateUserRoleMapper.delete(wrapperRole);

        cacheService.clearUserAllCache(uid);
        return rows>0;
    }

    @Override
    @Transactional
    public boolean deleteGroup(Long ugid) {
        int rows = userGroupMapper.deleteById(ugid);

        QueryWrapper<RelateGroupUser> wrapperGroup = new QueryWrapper<>();
        wrapperGroup.eq("ug_id",ugid);
        relateGroupUserMapper.delete(wrapperGroup);

        QueryWrapper<RelateGroupRole> wrapperRole = new QueryWrapper<>();
        wrapperRole.eq("ug_id",ugid);
        relateGroupRoleMapper.delete(wrapperRole);

        return rows>0;
    }

    @Override
    @Transactional
    public boolean deleteRole(Long rid) {
        int rows = roleMapper.deleteById(rid);

        QueryWrapper<RelateGroupRole> wrapperRole1 = new QueryWrapper<>();
        wrapperRole1.eq("r_id",rid);
        relateGroupRoleMapper.delete(wrapperRole1);

        QueryWrapper<RelateUserRole> wrapperRole2 = new QueryWrapper<>();
        wrapperRole2.eq("r_id", rid);
        relateUserRoleMapper.delete(wrapperRole2);

        QueryWrapper<RelateRolePower> wrapperPower = new QueryWrapper<>();
        wrapperPower.eq("r_id", rid);
        relateRolePowerMapper.delete(wrapperPower);

        return rows>0;
    }

    @Override
    @Transactional
    public boolean deletePower(Long pid) {
        int rows = powerMapper.deleteById(pid);

        QueryWrapper<RelateRolePower> wrapperPower = new QueryWrapper<>();
        wrapperPower.eq("p_id", pid);
        relateRolePowerMapper.delete(wrapperPower);

        return rows>0;
    }

}
