package com.ktech.starter.configurations;


import com.ktech.starter.vaults.RDSConfigurationVault;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        value="rds.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class DataSourceConfiguration {



    @Bean
    public HikariDataSource getDatasource(@Autowired RDSConfigurationVault vault) {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(vault.getDBDriver());
        config.setJdbcUrl(vault.getDBURL());
        config.setUsername(vault.getDBUserName());
        config.setPassword(vault.getDBPassword());
        config.setConnectionTestQuery("select 1 from dual");
        config.setMaximumPoolSize(5);

        return new HikariDataSource(config);
    }



}
