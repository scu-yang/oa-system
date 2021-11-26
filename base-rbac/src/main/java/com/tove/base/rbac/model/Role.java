package com.tove.base.rbac.model;

import com.tove.infra.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Role extends BaseModel {
    /**
     * 角色名
     */
    private String name;
}
