package org.account.cl;

import org.account.cl.impl.redis.UserDaoRedisImpl;

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
     * 根据条件查询用户的數量
     * @param query
     * @return
     */
    //int getUsersCount(UserQuery query);

    /**
     * 根据条件查询用户信息
     * @param query
     * @return
     */
    //List<User> getUsers(UserQuery query);


    enum USER_OP {
        // 操作指标
        ADD, GET, DEL
    }
}
