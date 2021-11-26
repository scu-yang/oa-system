package com.tove.oa.account.api;

import com.tove.oa.account.api.common.Constant;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;

@Api(value = "用户信息接口")
@FeignClient(name = Constant.API_NAME)
public interface RBACApi {
}
