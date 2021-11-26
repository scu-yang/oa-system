package com.tove.oa.account.dao.model;

import com.tove.infra.common.BaseModel;
import lombok.Data;

import java.util.Date;

@Data
public class OaUser extends BaseModel {
    /**
     * 系统uid
     */
    private Long uid;

    /**
     * password
     */
    private String pwd;

    /**
     * 姓名
     */
    private String name;

    /**
     * 男(0) 女(1)
     */
    private int sex;

    /**
     * 出生年月
     */
    private Date birthDate;

    /**
     * 入职年月
     */
    private Date hireDate;

    /**
     * 有效(1) 无效(0)
     */
    private int valid;

}
