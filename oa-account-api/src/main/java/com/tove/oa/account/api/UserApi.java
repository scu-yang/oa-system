package com.tove.oa.account.api;

import com.tove.infra.common.Response;
import com.tove.oa.account.api.common.Constant;
import com.tove.oa.account.api.req.GetUserInfoReq;
import com.tove.oa.account.api.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "用户信息接口")
@FeignClient(name = Constant.API_NAME)
public interface UserApi {

    @ApiOperation("查询用户信息")
    @PostMapping(value = "/user/get-user-info", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<UserInfoVo> getUserInfo(@RequestBody @Validated GetUserInfoReq req);

    @ApiOperation("查询用户uid, 通过account_uid")
    @PostMapping(value = "/user/get-uid-by-account-uid", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<Long> getUserBaseUid(Long uid);
}
