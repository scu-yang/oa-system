package com.tove.oa.account.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients(value = {"com.tove.market.api"})
@MapperScan("com.tove.base.rbac.dao")
@ComponentScan(basePackages = {"com.tove.oa.account","com.tove.base.rbac"})
@SpringBootApplication(scanBasePackages = {"com.tove.base.rbac", "com.tove.oa.account"})
public class AccountRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountRestApplication.class);
    }
}
