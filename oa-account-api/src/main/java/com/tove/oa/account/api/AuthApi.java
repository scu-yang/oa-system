package com.tove.oa.account.api;

import com.tove.infra.common.Response;
import com.tove.oa.account.api.common.Constant;
import com.tove.oa.account.api.req.ChangePasswordReq;
import com.tove.oa.account.api.req.CheckPasswordReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@Api(value = "注册,登录, 权限认证接口")
@FeignClient(name = Constant.API_NAME)
public interface AuthApi {

    @ApiOperation("校验用户名和密码")
    @PostMapping(value = "/auth/check-password", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<Boolean> checkPassword(CheckPasswordReq req);

    @ApiOperation("修改密码")
    @PostMapping(value = "/auth/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<Boolean> changePassword(ChangePasswordReq req);
}
