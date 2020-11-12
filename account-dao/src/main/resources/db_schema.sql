# 用户基础表

create table user (
    username varchar(32) not null comment '用户的标志 唯一',
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

# 添加两个超管吧  密码都是123456

replace into user values
    ('chenglei', '$2a$10$WEcaImJsVZdJuog0UnDXe.V8BhBZ5UXRS.ZyNAG108/LBUn5s9MJy', 1, 0, 0, 0, now(), now()),
    ('jiapeng', '$2a$10$RuGRFjrR6nBEGRwj4V23R.Fd5JNDb.sA/4xdMezJdawPge3zAH6XW', 1, 0, 0, 0, now(), now());

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

# 添加一个超级权限 主要用于系统管理员

replace into permission values (1, '超级权限', '/**', 'ALL', 'ALL', 1, now(), now());

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

insert into role values (1, 0, 'super', 'SYSTEM', 1, now(), now());

# 角色表 函数创建
# 查询指定 roleId 和 其子 roleId

delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `getSubRole`(id int) RETURNS varchar(1000) CHARSET utf8
begin
    declare ptemp varchar(1000);
    declare ttemp varchar(1000);
    set ttemp = '#';
    set ptemp = cast(id as char);
    while ptemp is not null do
        set ttemp = concat(ttemp,",",ptemp);
        select group_concat(sid) into ptemp from role where find_in_set(pid, ptemp);
        #if ptemp is not null then
        #end if;
    end while;
    return REPLACE(ttemp, '#,', '');
end $$
delimiter;

# 查询指定 多个RoleId 和它的子roleId

delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `getSubRoleArr`(ids varchar(500)) RETURNS varchar(1000) CHARSET utf8
begin
    declare tindex int;
    declare result varchar(1000);
    declare element varchar(1000);
    set result = '#';
    set tindex = length(ids) - length(replace(ids, ',', '')) + 1;
    while tindex > 0 do
        set tindex = tindex - 1;
        set element = SUBSTRING_INDEX(ids, ',', -1);
        set result = CONCAT(result, ',', getSubRole(element));
        set ids = REPLACE(ids, CONCAT(',', element), '');
    end while;
    return REPLACE(result, '#,', '');
end $$
delimiter;

## 角色权限关系表
create table role_permission (
    role_id int not null comment '角色id',
    permission_id int not null comment '权限id',
    update_date timestamp not null default '2020-10-31 00:00:00' comment '信息修改的时间',
    create_date timestamp not null default now() comment '信息创建的时间',
    primary key (role_id, permission_id),
    constraint fk_role_id foreign key(role_id) references role(sid) on delete restrict on update restrict,
    constraint fk_permission_id foreign key(permission_id) references permission(id) on delete restrict on update restrict,
    index idx_update_time (update_date)
) engine=InnoDB default charset=utf8 comment='角色权限关系表';

replace into role_permission values (1, 1, now(), now());

## 角色权限关系表

create table user_role (
    username varchar(32) not null comment '用户的标志',
    role_id int not null comment '角色id',
    update_date timestamp not null default '2020-10-31 00:00:00' comment '信息修改的时间',
    create_date timestamp not null default now() comment '信息创建的时间',
    primary key (username, role_id),
    constraint fk_u_role_id foreign key(role_id) references role(sid) on delete restrict on update restrict,
    constraint fk_username foreign key(username) references user(username) on delete restrict on update restrict,
    index idx_u_update_time (update_date)
) engine=InnoDB default charset=utf8 comment='用户角色关系表';

replace into user_role values ('chenglei', 1, now(), now()), ('jiapeng', 1, now(), now());

