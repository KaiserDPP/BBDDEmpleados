package com.example.configuration.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory", // Referencia a Bean 3
		transactionManagerRef = "mysqlTransactionManager", // Referencia a Bean 4
		basePackages = { "com.example.repository.accesos" }) // paquete donde se almacenan los repositorios de
																// JPA para la BBDD mysql
public class MysqlDataConfig {
	private final Environment env;

	public MysqlDataConfig(Environment env) {
		this.env = env;
	}

	@Primary // 1) Bean de properties que sale de lo que hay escrito en
				// application.properties
	@Bean(name = "mysqlDataSourceProperties")
	@ConfigurationProperties("spring.datasource-mysql")
	public DataSourceProperties mysqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary // 2) Bean de Datasource en bruto generado a partir del Bean de properties 1
	@Bean(name = "mysqlDataSource")
	@ConfigurationProperties("spring.datasource-mysql.configuration")
	public DataSource mysqlDataSource(
			@Qualifier("mysqlDataSourceProperties") DataSourceProperties mysqlDataSourceProperties) {
		return mysqlDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Primary // 3) Manager de entidades JPA creado a partir del Bean de Datasource 2
	@Bean(name = "mysqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
			EntityManagerFactoryBuilder mysqlEntityManagerFactoryBuilder,
			@Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
		Map<String, String> mysqlJpaProperties = new HashMap<>();
		mysqlJpaProperties.put("hibernate.dialect", env.getProperty("spring.datasource-mysql.hibernate.dialect"));
		mysqlJpaProperties.put("hibernate.hbm2ddl.auto",
				env.getProperty("spring.datasource-mysql.hibernate.hbm2ddl.auto"));
		return mysqlEntityManagerFactoryBuilder.dataSource(mysqlDataSource).packages("com.example.entity.accesos")
				.persistenceUnit("mysqlDataSource").properties(mysqlJpaProperties).build();
	}

	@Primary // 4) Manager de transacciones JPA creado a patir del Bean de manager de
				// entidades 3
	@Bean(name = "mysqlTransactionManager")
	public PlatformTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
		return new JpaTransactionManager(mysqlEntityManagerFactory);
	}
}
