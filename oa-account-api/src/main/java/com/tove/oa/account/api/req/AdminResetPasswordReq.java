package com.tove.oa.account.api.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class AdminResetPasswordReq {
    @NotNull
    private Long adminAccountUid;

    @NotNull
    private Long accountUid;
}
