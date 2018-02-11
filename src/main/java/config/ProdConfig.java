package config;

import email.BayesEmailAlgorithm;
import email.BayesEmailProbabilityTrainer;
import email.ProbabilityCalculator;
import email.ProbabilityTrainer;
import email.SpamAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan("data")
@EnableJpaRepositories("data")
@EnableTransactionManagement
public class ProdConfig {
    @Bean
    public ProbabilityTrainer trainer() {
        return new BayesEmailProbabilityTrainer();
    }

    @Bean
    public ProbabilityCalculator calculator() {
        return new ProbabilityCalculator();
    }

    @Bean
    public SpamAlgorithm algorithm() {
        return new BayesEmailAlgorithm();
    }

    @Bean
    public DataSource dataSource(@Value("jdbc.driver") String driver,
                                 @Value("jdbc.url") String url,
                                 @Value("jdbc.username") String user,
                                 @Value("jdbc.password") String pass) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("data");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}
