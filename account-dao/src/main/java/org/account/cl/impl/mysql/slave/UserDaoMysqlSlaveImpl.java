package org.account.cl.impl.mysql.slave;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 基础用户信息
 * @author Administrator
 */
@Mapper
public interface UserDaoMysqlSlaveImpl extends UserDao {

    String USER_TAB = "user";

    /**
     * 根据用户名取得用户的完整信息
     * @param username
     * @return
     */
    @Override
    @Select("select * from " + USER_TAB + " where username = #{username}")
    User getUserByUsername(String username);
}
