package org.account.cl.permissions.impl;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.permissions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDaoCacheImpl")
    private UserDao userDaoCacheImpl;

    @Override
    public boolean addUser(User user) {
        return userDaoCacheImpl.addUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDaoCacheImpl.getUserByUsername(username);
    }
}
