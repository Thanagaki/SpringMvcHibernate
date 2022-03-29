package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import web.model.User;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(value = "web")
public class HibernateConfig {

    private final Environment environment;

    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("db.driver"));
        driverManagerDataSource.setUrl(environment.getProperty("db.url"));
        driverManagerDataSource.setPassword(environment.getProperty("db.password"));
        driverManagerDataSource.setUsername(environment.getProperty("db.username"));
        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localEntityManagerFactoryBean.setDataSource(getDataSource());
        localEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        props.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        localEntityManagerFactoryBean.setJpaProperties(props);
        localEntityManagerFactoryBean.setPackagesToScan(User.class.getPackage().getName());

        return localEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return transactionManager;

    }
}
