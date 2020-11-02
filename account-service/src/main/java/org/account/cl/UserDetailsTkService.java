package org.account.cl;

import org.account.cl.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Administrator
 */
public interface UserDetailsTkService extends UserDetailsService {

    /**
     * 根据用户名 取得用户的详细信息
     * @param username 用户名
     * @return 用户的详细信息
     */
    User getUserByName(String username);
}
