package com.tove.base.rbac.model;

import com.tove.infra.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class RelateGroupRole extends BaseModel {
    /**
     * user group id
     */
    private Long ugId;

    /**
     * role id
     */
    private Long rid;
}
