package com.report.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class})
@EnableSwagger2
public class AppConfiguration {
	
	 @Value("${db.driver}")
	    private String DB_DRIVER;

	    @Value("${db.password}")
	    private String DB_PASSWORD;

	    @Value("${db.url}")
	    private String DB_URL;

	    @Value("${db.username}")
	    private String DB_USERNAME;

	    @Value("${hibernate.dialect}")
	    private String HIBERNATE_DIALECT;

	    @Value("${hibernate.show_sql}")
	    private String HIBERNATE_SHOW_SQL;

	    @Value("${hibernate.hbm2ddl.auto}")
	    private String HIBERNATE_HBM2DDL_AUTO;

	    @Value("${entitymanager.packagesToScan}")
	    private String ENTITYMANAGER_PACKAGES_TO_SCAN;
	    
	    @Value("${mail_username}")
	    private String username;
		
		@Value("${mail_password}")
	    private String password;
	   
	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	        Properties hibernateProperties = new Properties();
	        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
	        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
	        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	        sessionFactory.setHibernateProperties(hibernateProperties);
	        return sessionFactory;
	    }

	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(DB_DRIVER);
	        dataSource.setUrl(DB_URL);
	        dataSource.setUsername(DB_USERNAME);
	        dataSource.setPassword(DB_PASSWORD);
	        return dataSource;
	    }

	    @Bean
	    public HibernateTransactionManager transactionManager() {
	        HibernateTransactionManager txManager = new HibernateTransactionManager();
	        txManager.setSessionFactory(sessionFactory().getObject());
	        return txManager;
	    }
	    
	    @Bean
		public Docket postsApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.report.controller"))
	                .paths(regex("/report.*"))
	                .build()
	                .apiInfo(metaData());
		}
	    
	    private ApiInfo metaData() {
	        ApiInfo apiInfo = new ApiInfo(
	                "Spring Boot REST API",
	                "Spring Boot REST API for Report",
	                "1.0",
	                "Terms of service",
	                new Contact("Sunny Singh", "https://techwisegital.com", "sunny.singh@techwisedigital.com"),
	               "Apache License Version 2.0",
	                "https://techwisegital.com");
	        return apiInfo;

	    }

		

}
