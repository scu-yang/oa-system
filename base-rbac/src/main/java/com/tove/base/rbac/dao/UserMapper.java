package com.tove.base.rbac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tove.base.rbac.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
