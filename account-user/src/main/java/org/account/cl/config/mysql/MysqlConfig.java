package org.account.cl.config.mysql;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
public class MysqlConfig {
    final static String MASTER_MAP_PATH = "org.account.cl.impl.mysql.master";
    final static String MASTER_DATASOURCE = "masterDataSource";
    final static String MASTER_CONFIG_PROPERTIES = "spring.datasource.master";
    final static String MASTER_SESSION_TEMPLATE = "masterSqlSessionTemplate";
    final static String MASTER_SESSION_FACTORY = "masterSessionFactory";
    final static String MASTER_TRANSACTION_MANAGER = "masterTransactionManager";
    final static String SLAVE_MAP_PATH = "org.account.cl.impl.mysql.slave";
    final static String SLAVE_DATASOURCE = "slaveDataSource";
    final static String SLAVE_CONFIG_PROPERTIES = "spring.datasource.slave";
    final static String SLAVE_SESSION_TEMPLATE = "slaveSqlSessionTemplate";
    final static String SLAVE_SESSION_FACTORY = "slaveSessionFactory";
    final static String SLAVE_TRANSACTION_MANAGER = "slaveTransactionManager";

    @Bean
    @ConfigurationProperties(prefix="mybatis.configuration")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }
}
