package com.tove.base.rbac.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tove.base.rbac.model.Power;
import com.tove.base.rbac.model.Role;
import com.tove.base.rbac.model.User;
import com.tove.base.rbac.model.UserGroup;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
@Service
public class CacheService {
    private Cache<Long, List<Role>> userRoleCache;
    private Cache<Long, List<Power>> userPowerCache;
    private Cache<Long, List<UserGroup>> userGroupCache;

    private Cache<Long, List<Power>> powerInRoleCache;
    private Cache<Long, List<Role>> roleInGroupCache;
    private Cache<Long, List<User>> userInGroupCache;

    public CacheService(){
        userRoleCache = Caffeine.newBuilder()
                .initialCapacity(1_000)//初始大小
                .maximumSize(4_000)//最大数量
                .expireAfterWrite(60, TimeUnit.SECONDS)//过期时间
                .build();

        userPowerCache = Caffeine.newBuilder()
                .initialCapacity(1_000)
                .maximumSize(4_000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();

        userGroupCache = Caffeine.newBuilder()
                .initialCapacity(1_000)
                .maximumSize(4_000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();

        powerInRoleCache = Caffeine.newBuilder()
                .initialCapacity(1_000)
                .maximumSize(4_000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();

        roleInGroupCache = Caffeine.newBuilder()
                .initialCapacity(1_000)
                .maximumSize(4_000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();

        userGroupCache = Caffeine.newBuilder()
                .initialCapacity(1_000)
                .maximumSize(4_000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }
}
