package com.tove.oa.account.api;

import com.tove.infra.common.Response;
import com.tove.oa.account.api.common.Constant;
import com.tove.oa.account.api.req.AdminResetPasswordReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@Api(value = "管理员接口, 需要普通管理员权限")
@FeignClient(name = Constant.API_NAME)
public interface AdminApi {

    @ApiOperation("修改用户密码")
    @PostMapping(value = "/admin/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<Boolean> changePassword(AdminResetPasswordReq req);




}
