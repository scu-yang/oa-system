package com.tove.base.rbac.dao;

import com.tove.base.rbac.model.Power;
import com.tove.base.rbac.model.Role;
import com.tove.base.rbac.model.User;
import com.tove.base.rbac.model.UserGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO `user`(username) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(String name);

    @Select("select id, name from `role` r where r.id in \n" +
            "(select rur.rid from `user` u2 join relate_user_role rur on u2.id =rur.uid where u2.id=#{uid})")
    List<Role> getUserRoles(Long uid);

    @Select("select id, name from user_group ug \n" +
            "where ug.id in (select rgu.ug_id from relate_group_user rgu where rgu.uid=#{uid})")
    List<UserGroup> getUserGroups(Long uid);

    @Select("<script>" +
            "select * from `role` r  join relate_group_role rgr on rgr.ug_id = r.id  \n" +
            "<where> " +
            "rgr.ug_id in \n" +
                "<foreach item='item' collection='list' separator=',' open='(' close=')' index=''> \n" +
                " #{item}" +
                "</foreach>" +
            "</where>" +
            "</script>")
    List<Role> getUserGroupRoles(@Param("list") List<Long> groupIdList);


    @Select("<script>" +
            "select * from `power` p join relate_role_power rrp on rrp.pid = p.id \n" +
            "<where> " +
            "rrp.rid in " +
                "<foreach item='item' collection='list' separator=',' open='(' close=')' index=''> \n" +
                " #{item}" +
                "</foreach>" +
            "</where>" +
            "</script>")
    List<Power> getPowersByRoles(@Param("list") List<Long> roleIdList);

    @Select("select r2.id, r2.name from `role` r2 join relate_group_role rgr on r2.id = rgr.rid where rgr.ug_id = #{ugid}")
    List<Role> getRoleInGroup(Long ugid);

    @Select("select p.id, p.name from `power` p join relate_role_power rrp on rrp.pid = p.id where rrp.rid =#{rid}")
    List<Power> getPowerInRole(Long rid);

    @Select("select u2.id, u2.username from `user` u2 join  relate_group_user rgu on rgu.uid = u2.id where rgu.ug_id =#{ugid}")
    List<User> getUserInGroup(Long ugid);
}
