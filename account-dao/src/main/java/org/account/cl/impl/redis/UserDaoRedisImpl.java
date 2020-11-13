package org.account.cl.impl.redis;

import org.account.cl.UserDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Repository
@ConditionalOnBean(name="appApplication")
public class UserDaoRedisImpl {

    private RedisUtils<String, String> redisString;

    public UserDaoRedisImpl(RedisTemplate userRedis) {
        redisString = new RedisUtils<String, String>(userRedis);
    }

    /**
     * 是否添加一个登录失败的统计 true添加 false删除该统计 默认60秒
     * @param username
     * @param op
     * @return 添加后统计的数目
     */
    public long loginNum(String username, UserDao.USER_OP op) {
        switch (op) {
            case ADD: {
                long num = redisString.incr(username);
                redisString.expire(username, 60 * 60);
                return num;
            }
            case DEL: redisString.del(username); return 0L;
            case GET: {
                String val = redisString.get(username, String.class);
                return val == null ? 0 : Long.parseLong(val);
            }
            default: return Integer.MAX_VALUE;
        }
    }
}
