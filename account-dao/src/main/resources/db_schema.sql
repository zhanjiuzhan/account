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


# 权限表

create table permission (
    id int not null AUTO_INCREMENT comment '权限的标志 唯一',
    name varchar(32) not null default '' comment '权限描述',
    url varchar(128) not null default '' comment '请求路径',
    method varchar(16) not null default '' comment '请求类型',
    project varchar(32) not null default '' comment '项目代号',
    status tinyint not null default 0 comment '权限状态 0 新增 1 已确认 2 已经没有了可以自行删除',
    update_date timestamp not null default '2020-10-31 00:00:00' comment '用户信息修改的时间',
    create_date timestamp not null default now() comment '用户信息创建的时间',
    primary key (id),
    index idx_url (url),
    index idx_project (project)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

# 项目表

create table project (
    name varchar(32) not null default '' comment '项目简称',
    url varchar(256) not null default '' comment '请求路径',
    description varchar(256) not null default '' comment '项目描述',
    update_date timestamp not null default '2020-10-31 00:00:00' comment '信息修改的时间',
    create_date timestamp not null default now() comment '信息创建的时间',
    primary key (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';

# 角色表

create table role (
    sid int not null AUTO_INCREMENT comment '角色的标志 唯一',
    pid int not null default 0 comment '角色的标志 sid 的一个 0 代表根',
    name varchar(32) not null default '' comment '角色简称',
    description varchar(32) not null default '' comment '角色描述',
    status tinyint not null default 0 comment '0 已禁用 1 可用 2 要重新指定pid',
    update_date timestamp not null default '2020-10-31 00:00:00' comment '信息修改的时间',
    create_date timestamp not null default now() comment '信息创建的时间',
    primary key (sid),
    index idx_pid (pid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

# 角色表 函数创建

delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `getSubRole`(id int) RETURNS varchar(1000) CHARSET utf8
begin
    declare ptemp varchar(1000);
    declare ttemp varchar(1000);
    set ttemp = '#';
    set ptemp = cast(id as char);
    while ptemp is not null do
        select group_concat(sid) into ptemp from role where find_in_set(pid, ptemp);
        if ptemp is not null then
            set ttemp = concat(ttemp,",",ptemp);
        end if;
    end while;
    return ttemp;
end $$
delimiter;
