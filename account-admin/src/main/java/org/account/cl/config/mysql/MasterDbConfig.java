package org.account.cl.config.mysql;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Administrator
 */
@Configuration
@MapperScan(basePackages = MysqlConst.MASTER_MAP_PATH, sqlSessionTemplateRef = MysqlConst.MASTER_SESSION_TEMPLATE)
public class MasterDbConfig {
    /**
     * 生成数据源.  @Primary 注解声明为默认数据源
     * Primary 说明是默认数据源
     */
    @Bean(name = MysqlConst.MASTER_DATASOURCE)
    @ConfigurationProperties(prefix = MysqlConst.MASTER_CONFIG_PROPERTIES)
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建 SqlSessionFactory
     */
    @Bean(name = MysqlConst.MASTER_SESSION_FACTORY)
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier(MysqlConst.MASTER_DATASOURCE) DataSource dataSource,
                                                   @Qualifier("globalConfiguration") org.apache.ibatis.session.Configuration globalConfiguration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfiguration(globalConfiguration);
        return bean.getObject();
    }

    /**
     * 配置事务管理
     */
    @Bean(name = MysqlConst.MASTER_TRANSACTION_MANAGER)
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier(MysqlConst.MASTER_DATASOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = MysqlConst.MASTER_SESSION_TEMPLATE)
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier(MysqlConst.MASTER_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
