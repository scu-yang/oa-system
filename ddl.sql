CREATE TABLE `oa_user` (
    `id` BIGINT(20) UNSIGNED auto_increment COMMENT '主键',
    `uid` BIGINT(20) UNSIGNED NOT NULL COMMENT '系统uid',
    `pwd` varchar(100) NOT NULL COMMENT 'password',
    `name` varchar(50) NOT NULL COMMENT '姓名',
    `sex` tinyint(1) NULL COMMENT '男(0) 女(1)',
    `birth_date` TIMESTAMP NULL COMMENT '出生年月',
    `hire_date` TIMESTAMP NULL COMMENT '入职年月',
    `valid` tinyint(1) NOT NULL DEFAULT 1 COMMENT '有效(1) 无效(0)',
    `db_create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `db_modify_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB COMMENT = '用户信息表';