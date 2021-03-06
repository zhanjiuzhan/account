package org.account.cl;

import org.account.cl.condition.UserQuery;

import java.util.List;

/**
 * 基础用户信息
 * @author Administrator
 */
public interface UserDao {

    /**
     * 添加一个用户信息
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 根据用户名取得用户的完整信息
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 是否添加一个登录失败的统计 true添加 false删除该统计
     * @param username
     * @param op
     * @return 添加后统计的数目
     */
    long loginNum(String username, USER_OP op);


    /**
     * 取得所有的用户信息
     * @return
     */
    List<User> gets();

    /**
     * 根据条件查询用户的信息 有分页
     * @param query
     * @return
     */
    int getsByConditionCount(UserQuery query);

    /**
     * 根据条件查询用户的信息 有分页 可以无分页限制
     * @param query
     * @return
     */
    List<User> getsByCondition(UserQuery query);

    /**
     * 用户信息修改
     * @param username
     * @param query
     * @return
     */
    boolean updateUser(String username, UserQuery query);

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    boolean deleteUser(String username);


    enum USER_OP {
        // 操作指标
        ADD, GET, DEL
    }
}
