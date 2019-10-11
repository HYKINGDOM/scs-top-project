package com.scs.top.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 新的开始
 * @author yihur
 *
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"com.scs.top.project.module.*.mapper"})
public class ScsTopProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScsTopProjectApplication.class, args);
    }

}
