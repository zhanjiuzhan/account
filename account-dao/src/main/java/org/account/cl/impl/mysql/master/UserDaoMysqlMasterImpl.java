package org.account.cl.impl.mysql.master;

import org.account.cl.User;
import org.account.cl.condition.UserQuery;
import org.account.cl.impl.mysql.UserDaoMysqlImplProvider;
import org.apache.ibatis.annotations.*;

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

    /**
     * 用户信息修改
     * @param username
     * @param query
     * @return
     */
    @UpdateProvider(type = UserDaoMysqlImplProvider.class, method = "updateUser")
    boolean updateUser(String username, UserQuery query);

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    @Delete("delete from " + USER_TAB + " where username = #{username}")
    boolean deleteUser(String username);
}
