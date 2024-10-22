package com.example.hakaton.config;

import com.example.hakaton.util.AuditorAwareUtil;
import com.example.hakaton.util.other.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * @author Zhumaev.Ali
 * @email seasonvar21@gmail.com
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
public class AuditConfig {
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public AuditConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public AuditorAware<String> aware() {
        return new AuditorAwareUtil();
    }

    @Bean
    public CustomIdGenerator customIdGenerator() {
        return new CustomIdGenerator();
    }
}
