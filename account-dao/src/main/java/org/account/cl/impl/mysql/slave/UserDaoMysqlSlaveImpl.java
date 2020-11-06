package org.account.cl.impl.mysql.slave;

import org.account.cl.User;
import org.account.cl.condition.UserQuery;
import org.account.cl.impl.mysql.UserDaoMysqlImplProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 基础用户信息
 * @author Administrator
 */
@Mapper
public interface UserDaoMysqlSlaveImpl {

    String USER_TAB = "user";

    /**
     * 根据用户名取得用户的完整信息
     * @param username
     * @return
     */
    @Select("select *  from " + USER_TAB + " where username = #{username} limit 1")
    User getUserByUsername(String username);

    /**
     * 取得所有的用户信息
     * @return
     */
    @Select("select *  from " + USER_TAB + " order by update_date desc")
    List<User> gets();

    /**
     * 根据条件查询用户的信息 有分页
     * @param query
     * @return
     */
    @SelectProvider(type = UserDaoMysqlImplProvider.class, method = "getsByConditionCount")
    int getsByConditionCount(UserQuery query);

    /**
     * 根据条件查询用户的信息 有分页 可以无分页限制
     * @param query
     * @return
     */
    @SelectProvider(type = UserDaoMysqlImplProvider.class, method = "getsByCondition")
    List<User> getsByCondition(UserQuery query);
}
