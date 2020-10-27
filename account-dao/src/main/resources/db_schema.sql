# 用户基础表
create table user (
    username varchar(64) not null comment '用户的标志 唯一',
    password varchar(512) not null comment '用户认证 通常为密码',
    isEnable tinyint(1) not null default 1 comment '用户是否可用 0 不可用  1 可用',
    expired tinyint(1) not null default 0 comment '用户是否过期 0 没有 1过期',
    locked tinyint(1) not null default 0 comment '用户是否锁住 0 没有 1锁住',
    credentials_expired tinyint(1) not null default 0 comment '用户认证是否过期 0没 1过期',
    update_date timestamp not null default '2020-10-26 00:00:00' comment '用户信息修改的时间',
    create_date timestamp not null default now() comment '用户信息创建的时间',
    primary key (username),
    index idx_is_enable (isEnable),
    index idx_expired (expired),
    index idx_locked (locked),
    index idx_credentials_expired (credentials_expired),
    index idx_up_date (update_date),
    index idx_ad_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础信息表';
