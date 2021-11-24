package com.tove.oa.account.rest.controller;

import com.tove.infra.common.Response;
import com.tove.market.api.DemoApi;
import com.tove.oa.account.api.UserApi;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController implements UserApi {
    @Resource
    private DemoApi demoApi;


    @Override
    public Response<Boolean> addUser(String req) {
        com.example.common.Response<Boolean> response = demoApi.addGroup("test");
        System.out.println(response);
        return Response.getFail();
    }
}
