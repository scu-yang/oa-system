package com.tove.oa.account.api;

import com.tove.infra.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "用户信息接口")
@FeignClient(name = "oa-account")
public interface UserApi {

    @ApiOperation("查询用户信息")
    @PostMapping(value = "/user/add-group", produces = MediaType.APPLICATION_JSON_VALUE)
    Response<Boolean> addUser(@RequestBody @Validated String req);
}
