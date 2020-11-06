package org.account.cl;

import org.account.cl.condition.UserQuery;

import java.util.List;

/**
 * @author Administrator
 */
public interface UserService {

    /**
     * 添加一个用户信息
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 根据用户名取得用户信息
     * @param username 不能是空的
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 取得一个解密的密码 从前端
     * @param encodePassword
     * @return
     */
    String getDecodePassword(String encodePassword);

    /**
     * 是否添加一个登录失败的统计 true添加 false删除该统计
     * @param username
     * @param op
     * @return 添加后统计的数目
     */
    long loginNum(String username, UserDao.USER_OP op);

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

    /**
     * 是不是同一个用户
     * @param username
     * @param password
     * @return
     */
    boolean isOneUser(String username, String password);

    /**
     * 用户是否可用
     * @param username
     * @return
     */
    boolean isValidUser(String username);
}
