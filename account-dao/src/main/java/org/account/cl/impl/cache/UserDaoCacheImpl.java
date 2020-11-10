package org.account.cl.impl.cache;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.condition.UserQuery;
import org.account.cl.impl.mysql.master.UserDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.slave.UserDaoMysqlSlaveImpl;
import org.account.cl.impl.redis.UserDaoRedisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO 缓存技术
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

    @Override
    public List<User> gets() {
        return userDaoMysqlSlaveImpl.gets();
    }

    @Override
    public int getsByConditionCount(UserQuery query) {
        return userDaoMysqlSlaveImpl.getsByConditionCount(query);
    }

    @Override
    public List<User> getsByCondition(UserQuery query) {
        return userDaoMysqlSlaveImpl.getsByCondition(query);
    }

    @Override
    public boolean updateUser(String username, UserQuery query) {
        return userDaoMysqlMasterImpl.updateUser(username, query);
    }

    @Override
    public boolean deleteUser(String username) {
        return userDaoMysqlMasterImpl.deleteUser(username);
    }
}
