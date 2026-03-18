package org.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
@ComponentScan("org.example.*")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class projConfig {

    @Bean
    public DataSource datasource() {
        var datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/cart");
        datasource.setUsername("root");
        datasource.setPassword("Dhak$hna02");

        return datasource;
    }

    @Bean
    public JdbcTemplate getjdbctemplate(DataSource datasource){
        return  new JdbcTemplate(datasource);
    }


    @Bean
    public DataSourceTransactionManager transcationManager(DataSource dataSource){
        return  new DataSourceTransactionManager(dataSource);
    }
}
