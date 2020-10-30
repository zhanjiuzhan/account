package org.account.cl.impl.mysql.master;

import org.account.cl.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基础用户信息
 * @author Administrator
 */
@Mapper
public interface UserDaoMysqlMasterImpl {

    String USER_TAB = "user";

    /**
     * 添加一个用户信息
     * @param user
     * @return
     */
    @Insert("insert into " + USER_TAB + " (username, password, isEnable, expired, locked, credentials_expired, update_date, create_date) values " +
            "(#{username}, #{password}, #{isEnable}, #{expired}, #{locked}, #{credentialsExpired}, now(), now())")
    boolean addUser(User user);
}
