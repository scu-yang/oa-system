package com.tove.oa.account.rest.controller;

import com.tove.infra.common.Response;
import com.tove.oa.account.api.AdminApi;
import com.tove.oa.account.api.req.AdminResetPasswordReq;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController implements AdminApi {
    @Override
    public Response<Boolean> changePassword(AdminResetPasswordReq req) {
        return null;
    }
}
