package org.account.cl.permissions.impl;

import org.account.cl.User;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.permissions.UserDetailsTkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UserDetailsTkServiceImpl implements UserDetailsTkService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUserByName(String username) {
        ExceptionEnum.INVALID_PARAMETER.assertNotNull(username, "username", "用户名不能为null", username);

        // 此处暂时使用假的信息
        User user = new User();
        user.setUsername("dw_chenglei");
        user.setPassword(bCryptPasswordEncoder.encode("123"));
        user.setCredentialsExpired(true);
        user.setEnabled(true);
        user.setExpired(true);
        user.setLocked(true);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        grantedAuthorities.add(grantedAuthority);

        user.setAuthorities(grantedAuthorities);
        return user;
    }

    /**
     * security的UserDetailsService的接口方法
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserByName(username);
    }
}
