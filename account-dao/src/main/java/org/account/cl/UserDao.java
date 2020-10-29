package org.account.cl;

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
}
