package org.account.cl.impl.cache;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Repository
public class UserDaoCacheImpl implements UserDao {

    @Resource
    private UserDao userDaoMysqlMasterImpl;

    @Resource
    private UserDao userDaoMysqlSlaveImpl;

    @Override
    public boolean addUser(User user) {
        return userDaoMysqlMasterImpl.addUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDaoMysqlSlaveImpl.getUserByUsername(username);
    }
}
