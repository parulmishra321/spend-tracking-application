package com.flex83.app.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class MysqlProperties {
    @Value("${hibernate.connection.username}")
    private String hibernateConnectionUsername;
    @Value("${hibernate.connection.password}")
    private String hibernateConnectionPassword;
    @Value("${hibernate.connection.driver_class}")
    private String hibernateConnectionDriver;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.mt_url}")
    private String hibernateMtUrl;

    public String getHibernateConnectionUsername() {
        return hibernateConnectionUsername;
    }

    public void setHibernateConnectionUsername(String hibernateConnectionUsername) {
        this.hibernateConnectionUsername = hibernateConnectionUsername;
    }

    public String getHibernateConnectionPassword() {
        return hibernateConnectionPassword;
    }

    public void setHibernateConnectionPassword(String hibernateConnectionPassword) {
        this.hibernateConnectionPassword = hibernateConnectionPassword;
    }

    public String getHibernateConnectionDriver() {
        return hibernateConnectionDriver;
    }

    public void setHibernateConnectionDriver(String hibernateConnectionDriver) {
        this.hibernateConnectionDriver = hibernateConnectionDriver;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    public String getHibernateMtUrl() {
        return hibernateMtUrl;
    }

    public void setHibernateMtUrl(String hibernateMtUrl) {
        this.hibernateMtUrl = hibernateMtUrl;
    }
}
