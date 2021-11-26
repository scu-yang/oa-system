package com.oa.account.core.impl;

import com.oa.account.core.Userervice;
import com.tove.oa.account.dao.OaUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements Userervice {

    @Resource
    private OaUserMapper oaUserMapper;

    public void addUser(){
        oaUserMapper.insert(null);
    }
}
