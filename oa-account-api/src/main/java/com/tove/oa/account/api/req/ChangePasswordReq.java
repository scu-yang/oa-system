package com.tove.oa.account.api.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class ChangePasswordReq {
    @NotNull
    private Long accountUid;

    @NotEmpty
    @Length(min = 8, max = 20, message = "密码长度应该大于8位, 小于20位")
    private String newPassword;
}
