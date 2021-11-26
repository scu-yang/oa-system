package com.tove.oa.account.api.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CheckPasswordReq {

    @NotNull
    private Long accountUid;

    @NotEmpty
    private String password;

}
