package com.spendTracker.app.config;

import com.spendTracker.app.entities.TenantDatabaseConfig;
import com.spendTracker.app.props.MysqlProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static com.spendTracker.app.constant.ApplicationConstants.*;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.spendTracker.app.repository"})
public class JpaConfiguration {
    private static final Logger LOG = LogManager.getLogger(JpaConfiguration.class);
    @Autowired
    private MysqlProperties mysqlProperties;

    private void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(mysqlProperties.getHibernateMtUrl().split(DATABASE)[0], mysqlProperties.getHibernateConnectionUsername(), mysqlProperties.getHibernateConnectionPassword());
             Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate("CREATE DATABASE " + SPENDTRACKER_DATABASE);
            if (result > 0) {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                populator.setScripts(new ClassPathResource("datasource.sql"));

                DatabasePopulatorUtils.execute(populator, getDataSource());
            }
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1007) {
                LOG.info("Database already exists");
            }
        } finally {
            LOG.info("Database task performed");
        }
    }

    @PostConstruct
    public void init() {
        createDatabaseIfNotExists();
    }

    public DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(mysqlProperties.getHibernateConnectionUsername());
        hikariDataSource.setPassword(mysqlProperties.getHibernateConnectionPassword());
        hikariDataSource.setJdbcUrl(mysqlProperties.getHibernateMtUrl().split(DATABASE)[0] + SPENDTRACKER_DATABASE);
        hikariDataSource.setPoolName(SPENDTRACKER_DATABASE + " connection-pool");

        hikariDataSource.setMaximumPoolSize(100);
        hikariDataSource.setMinimumIdle(5);
        hikariDataSource.setConnectionTimeout(20000);
        hikariDataSource.setIdleTimeout(300000);

        return hikariDataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[]{TenantDatabaseConfig.class.getPackage().getName()});
        em.setPersistenceUnitName(SPENDTRACKER_DATABASE + " persistence-unit");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager setTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.DIALECT, mysqlProperties.getHibernateDialect());
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, false);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, false);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, HIBERNATE_HBM2DDL_AUTO);
        return properties;
    }
}
