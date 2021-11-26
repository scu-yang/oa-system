package com.tove.oa.account.api.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserInfoVo {

    private Long account_uid;
    private String name;
    private int sex;
    private Date birthDate;
    private Date hireDate;

    private List<RoleVo> roleList;
    private List<PowerVo> powerList;
    private List<UserGroupVo> groupList;
}
