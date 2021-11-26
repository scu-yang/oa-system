package com.tove.oa.account.rest.controller;

import com.tove.infra.common.Response;
import com.tove.oa.account.api.AuthApi;
import com.tove.oa.account.api.req.ChangePasswordReq;
import com.tove.oa.account.api.req.CheckPasswordReq;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    @Override
    public Response<Boolean> checkPassword(CheckPasswordReq req) {
        return null;
    }

    @Override
    public Response<Boolean> changePassword(ChangePasswordReq req) {
        return null;
    }
}
