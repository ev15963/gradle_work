package com.javateam.SpringBootBoard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class ProjectConfig extends WebMvcConfigurationSupport {

	// DBCP
	@Bean
	public DataSource dataSource(DataSourceProperties properties) {

		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(properties.getUrl());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		dataSource.setDriverClassName(properties.getDriverClassName());

		return new LazyConnectionDataSourceProxy(dataSource);
	}

	// transaction config
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Autowired @Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	// CSS, webjars config
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/");
		
		registry.addResourceHandler("/ckeditor4/**")
				.addResourceLocations("classpath:/static/ckeditor4/");
		
		registry.addResourceHandler("/upload_image/**")
				.addResourceLocations("classpath:/static/upload/upload_image/");
				
	}

}