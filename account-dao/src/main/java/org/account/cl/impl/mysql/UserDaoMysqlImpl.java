package org.account.cl.impl.mysql;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.condition.UserQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 基础用户信息
 * @author Administrator
 */
@Mapper
public interface UserDaoMysqlImpl extends UserDao {

    String USER_TAB = "user";

    /**
     * 根据用户名取得用户的完整信息
     * @param username
     * @return
     */
    @Override
    @Select("select * from " + USER_TAB + " where username = #{username}")
    User getUserByUsername(String username);

    /**
     * 添加一个用户信息
     * @param user
     * @return
     */
    @Override
    @Insert("insert into " + USER_TAB + " (username, password, isEnable, expired, locked, credentials_expired, update_date, create_date) values " +
            "(#{username}, #{password}, #{isEnable}, #{expired}, #{locked}, #{credentialsExpired}, now(), now())")
    boolean addUser(User user);
}
