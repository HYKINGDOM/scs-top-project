package com.scs.top.project.framework.druid;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 配置多数据源 事务管理
 * @author Administrator
 */
@Configuration
public class TransactionConfig {

    public final static String FIRST_TX = "firstTx";

    public final static String SECOND_TX = "secondTx";

    @Primary
    @Bean(name= TransactionConfig.FIRST_TX)
    public PlatformTransactionManager firstTxTransaction(@Qualifier("DynamicDataSource") DynamicDataSource masterDataSource){
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Bean(name= TransactionConfig.SECOND_TX)
    public PlatformTransactionManager secondTxTransaction(@Qualifier("DynamicDataSource") DynamicDataSource slaveDataSource){
        return new DataSourceTransactionManager(slaveDataSource);
    }


}
