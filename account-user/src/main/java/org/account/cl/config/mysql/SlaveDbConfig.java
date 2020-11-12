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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Administrator
 */
@Configuration
@MapperScan(basePackages = MysqlConst.SLAVE_MAP_PATH, sqlSessionTemplateRef = MysqlConst.SLAVE_SESSION_TEMPLATE)
public class SlaveDbConfig {
    /**
     * 生成数据源.  @Primary 注解声明为默认数据源
     */
    @Bean(name = MysqlConst.SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = MysqlConst.SLAVE_CONFIG_PROPERTIES)
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建 SqlSessionFactory
     */
    @Bean(name = MysqlConst.SLAVE_SESSION_FACTORY)
    public SqlSessionFactory testSqlSessionFactory(@Qualifier(MysqlConst.SLAVE_DATASOURCE) DataSource dataSource,
                                                   @Qualifier("globalConfiguration") org.apache.ibatis.session.Configuration globalConfiguration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfiguration(globalConfiguration);
        return bean.getObject();
    }

    /**
     * 配置事务管理
     */
    @Bean(name = MysqlConst.SLAVE_TRANSACTION_MANAGER)
    public DataSourceTransactionManager testTransactionManager(@Qualifier(MysqlConst.SLAVE_DATASOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = MysqlConst.SLAVE_SESSION_TEMPLATE)
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier(MysqlConst.SLAVE_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
