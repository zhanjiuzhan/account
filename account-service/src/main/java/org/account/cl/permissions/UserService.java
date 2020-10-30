package org.account.cl.permissions;

import org.account.cl.User;
import org.account.cl.UserDao;

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
}
