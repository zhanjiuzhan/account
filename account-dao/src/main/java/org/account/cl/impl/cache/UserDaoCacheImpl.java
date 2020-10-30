package org.account.cl.impl.cache;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.impl.mysql.master.UserDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.slave.UserDaoMysqlSlaveImpl;
import org.account.cl.impl.redis.UserDaoRedisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Repository
public class UserDaoCacheImpl implements UserDao {

    @Resource
    private UserDaoMysqlMasterImpl userDaoMysqlMasterImpl;

    @Resource
    private UserDaoMysqlSlaveImpl userDaoMysqlSlaveImpl;

    @Autowired(required = false)
    private UserDaoRedisImpl userDaoRedisImpl;

    @Override
    public boolean addUser(User user) {
        return userDaoMysqlMasterImpl.addUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDaoMysqlSlaveImpl.getUserByUsername(username);
    }

    @Override
    public long loginNum(String username, USER_OP op) {
        if (userDaoRedisImpl != null) {
            return userDaoRedisImpl.loginNum(username, op);
        }
        return 0;
    }
}
