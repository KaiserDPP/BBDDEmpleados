package com.example.configuration.flyway;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlyWayConfig {

	@Value("${spring.datasource-mysql.url}")
	String datasource_logicMysqlUrl;
	@Value("${spring.datasource-mysql.username}")
	String datasource_logicMysqlUser;
	@Value("${spring.datasource-mysql.password}")
	String datasource_logicMysqlPassword;
	/*@Value("${spring.datasource-mysql.schema}")
	String datasourceMySqlSchema;*/

	@Value("${spring.datasource-postgres.url}")
	String datasourcePostgresUrl;
	@Value("${spring.datasource-postgres.username}")
	String datasourcePostgresUser;
	@Value("${spring.datasource-postgres.password}")
	String datasourcePostgresPassword;
	/*@Value("${spring.datasource-postgres.schema}")
	String datasourcePostgresSchema;*/

	/*
	 * @Value("${spring.datasource-mariadb.url}") String
	 * datasource_logicMariadblUrl;
	 * 
	 * @Value("${spring.datasource-mariadb.username}") String
	 * datasource_logicMariadbUser;
	 * 
	 * @Value("${spring.datasource-mariadb.password}") String
	 * datasource_logicMariadbPassword;
	 */
	@PostConstruct
	public void migrateFlyway() {

		 // nombre de la
																									// tabla donde
																									// flyway guarda el
																									// histórico
		Flyway flywayMysql = Flyway.configure().cleanDisabled(true).table("accesos_schema_history")		
		.baselineOnMigrate(true).baselineVersion("-1").encoding(StandardCharsets.UTF_8)
				.schemas("accesos") // nombre de la bbdd que flyway va a mantener
				.validateOnMigrate(true)
				.dataSource(datasource_logicMysqlUrl, datasource_logicMysqlUser, datasource_logicMysqlPassword)
				.locations("filesystem:./src/main/resources/db/migration/mysql").load();

			
		Flyway flywayPostgres = Flyway.configure().cleanDisabled(true).table("local_schema_history")
				.baselineOnMigrate(true).baselineVersion("-1").encoding(StandardCharsets.UTF_8)
				.schemas("local").validateOnMigrate(true)
				.dataSource(datasourcePostgresUrl, datasourcePostgresUser, datasourcePostgresPassword)
				.locations("filesystem:./src/main/resources/db/migration/postgres").load();

		/*
		 * Flyway flywayMariaDB = Flyway.configure() .cleanDisabled(true)
		 * .table("mariadbhypedb_schema_history") //nombre de la tabla donde flyway
		 * guarda el histórico
		 * .baselineOnMigrate(true).baselineVersion("-1").encoding(StandardCharsets.
		 * UTF_8) .schemas("mariadbhypedb") //nombre de la bbdd que flyway va a mantener
		 * .validateOnMigrate(true).dataSource(datasource_logicMariadblUrl,
		 * datasource_logicMariadbUser, datasource_logicMariadbPassword)
		 * .locations("filesystem:./src/main/resources/db/migration/mariadb").load();
		 * 
		 * flywayMariaDB.migrate();
		 */
		flywayMysql.migrate();
		flywayPostgres.migrate();

	}
}
