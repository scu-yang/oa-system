package com.tove.oa.account.api.req;

import com.tove.oa.account.api.common.Constant;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;

@Api
@FeignClient(name = Constant.API_NAME)
public class GetUserInfoReq {

    private Long uid;

}
