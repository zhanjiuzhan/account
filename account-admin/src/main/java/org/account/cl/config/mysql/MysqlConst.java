package org.account.cl.config.mysql;

/**
 * @author Administrator
 */
public interface MysqlConst {
    String MASTER_MAP_PATH = "org.account.cl.impl.mysql.master";
    String MASTER_DATASOURCE = "masterDataSource";
    String MASTER_CONFIG_PROPERTIES = "spring.datasource.master";
    String MASTER_SESSION_TEMPLATE = "masterSqlSessionTemplate";
    String MASTER_SESSION_FACTORY = "masterSessionFactory";
    String MASTER_TRANSACTION_MANAGER = "masterTransactionManager";

    String SLAVE_MAP_PATH = "org.account.cl.impl.mysql.slave";
    String SLAVE_DATASOURCE = "slaveDataSource";
    String SLAVE_CONFIG_PROPERTIES = "spring.datasource.slave";
    String SLAVE_SESSION_TEMPLATE = "slaveSqlSessionTemplate";
    String SLAVE_SESSION_FACTORY = "slaveSessionFactory";
    String SLAVE_TRANSACTION_MANAGER = "slaveTransactionManager";
}
