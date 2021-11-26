package com.tove.base.rbac.service;

import com.tove.base.rbac.model.Power;
import com.tove.base.rbac.model.Role;
import com.tove.base.rbac.model.User;
import com.tove.base.rbac.model.UserGroup;

import java.util.List;

public interface AccountService {

    /** 获取一个用户的所有权限 */
    List<Power> getUserPowers(Long uid);

    /** 获取一个用户的所有角色 */
    List<Role> getUserRoles(Long uid);

    /**获取一个用户加入的所有用户组*/
    List<UserGroup> getUserGroups(Long uid);

    /**获取用户组里面的用户列表*/
    List<User> getUserInGroup(Long ugid);

    /** 获取某个角色的权限列表 */
    List<Power> getPowerInRole(Long rid);

    List<Role> getRoleInGroup(Long ugid);


    boolean addRolePower(Long rid, Long pid);

    boolean deleteRolePower(Long rid, Long pid);

    boolean addGroupRole(Long ugid, Long rid);

    boolean deleteGroupRole(Long ugid, Long rid);

    boolean addUserGroup(Long uid, Long ugid);

    boolean deleteUserGroup(Long uid, Long ugid);

    boolean addUserRole(Long uid, Long rid);

    boolean deleteUserRole(Long uid, Long rid);

    long addUser(String name);

    long addGroup(String name);

    long addRole(String name);

    long addPower(String name);

    boolean deleteUser(Long uid);

    boolean deleteGroup(Long ugid);

    boolean deleteRole(Long rid);

    boolean deletePower(Long pid);
}
