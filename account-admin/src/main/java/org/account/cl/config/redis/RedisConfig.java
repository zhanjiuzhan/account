package org.account.cl.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Administrator
 */
@Configuration
public class RedisConfig {

    @Autowired
    private UserRedisConf relationConf;

    @Bean("userRedis")
    public RedisTemplate<String, Object> userRedis() {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        redisTemplate.setConnectionFactory(getLettuceConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * user redis factory
     * @return
     */
    private LettuceConnectionFactory getLettuceConnectionFactory() {
        //redis配置
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(relationConf.getHost(), relationConf.getPort() );
        redisConfiguration.setDatabase(relationConf.getDatabase());

        //连接池配置
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(relationConf.getMaxIdle());
        genericObjectPoolConfig.setMinIdle(relationConf.getMinIdle());
        genericObjectPoolConfig.setMaxWaitMillis(relationConf.getMaxWait());
        genericObjectPoolConfig.setMaxTotal(relationConf.getMaxActive());

        //redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder =  LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();

        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
        lettuceConnectionFactory .afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    private RedisTemplate<String, Object> getRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置key
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 设置值
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }
}
