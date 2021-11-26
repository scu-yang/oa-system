package com.tove.base.rbac.model;

import com.tove.infra.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Power extends BaseModel {
    /**
     * 权限名
     */
    private String name;
}
