package com.tove.oa.account.rest.controller;

import com.tove.infra.common.Response;
import com.tove.market.api.DemoApi;
import com.tove.oa.account.api.UserApi;
import com.tove.oa.account.api.req.GetUserInfoReq;
import com.tove.oa.account.api.vo.UserInfoVo;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController implements UserApi {
    @Resource
    private DemoApi demoApi;


    @Override
    public Response<UserInfoVo> getUserInfo(GetUserInfoReq req) {
        return null;
    }

    @Override
    public Response<Long> getUserBaseUid(Long uid) {
        return null;
    }
}
