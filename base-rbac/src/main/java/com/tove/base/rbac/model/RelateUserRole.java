package com.tove.base.rbac.model;

import com.tove.infra.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class RelateUserRole extends BaseModel {
    /**
     * user id
     */
    private Long uid;

    /**
     * role id
     */
    private Long rid;
}
