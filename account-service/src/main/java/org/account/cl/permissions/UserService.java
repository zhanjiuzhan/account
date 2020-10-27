package org.account.cl.permissions;

import org.account.cl.User;

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
     * 根据用户有名取得用户信息
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
