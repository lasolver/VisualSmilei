package com.github.tubus.visual.smilei.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Primary
    @Bean(name = "smileiDb")
    @ConfigurationProperties(prefix = "spring.datasource.smilei")
    public DataSource prodOneSqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "smileiDbJdbc")
    public JdbcTemplate ProdOneJdbcTemplate(@Qualifier("smileiDb") DataSource dsProdOne) {
        return new JdbcTemplate(dsProdOne);
    }
}