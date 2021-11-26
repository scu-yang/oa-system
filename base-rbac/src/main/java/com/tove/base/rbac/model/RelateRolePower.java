package com.tove.base.rbac.model;

import com.tove.infra.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class RelateRolePower extends BaseModel {
    /**
     * role id
     */
    private Long rid;

    /**
     * role id
     */
    private Long pid;
}
