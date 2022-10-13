package com.vadrin.vtalk.configurations;


//[START cloud_sql_postgres_servlet_connect_connector]
//[START cloud_sql_postgres_servlet_connect_unix]
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConnectionPoolFactory extends ConnectionPoolFactory {

// Note: Saving credentials in environment variables is convenient, but not
// secure - consider a more secure solution such as
// Cloud Secret Manager (https://cloud.google.com/secret-manager) to help
// keep secrets safe.
private static final String INSTANCE_CONNECTION_NAME =
   System.getenv("INSTANCE_CONNECTION_NAME");
private static final String INSTANCE_UNIX_SOCKET = System.getenv("INSTANCE_UNIX_SOCKET");
private static final String DB_USER = System.getenv("DB_USER");
private static final String DB_PASS = System.getenv("DB_PASS");
private static final String DB_NAME = System.getenv("DB_NAME");
@Bean
public static DataSource createConnectionPool() {
 // The configuration object specifies behaviors for the connection pool.
 HikariConfig config = new HikariConfig();

 // The following URL is equivalent to setting the config options below:
 // jdbc:postgresql:///<DB_NAME>?cloudSqlInstance=<INSTANCE_CONNECTION_NAME>&
 // socketFactory=com.google.cloud.sql.postgres.SocketFactory&user=<DB_USER>&password=<DB_PASS>
 // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
 // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url

 // Configure which instance and what database user to connect with.
 config.setJdbcUrl(String.format("jdbc:postgresql:///%s", DB_NAME));
 config.setUsername(DB_USER); // e.g. "root", _postgres"
 config.setPassword(DB_PASS); // e.g. "my-password"

 config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
 config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);

 // [END cloud_sql_postgres_servlet_connect_connector]
 // Unix sockets are not natively supported in Java, so it is necessary to use the Cloud SQL
 // Java Connector to connect. When setting INSTANCE_UNIX_SOCKET, the connector will 
 // call an external package that will enable Unix socket connections.
 // Note: For Java users, the Cloud SQL Java Connector can provide authenticated connections
 // which is usually preferable to using the Cloud SQL Proxy with Unix sockets.
 // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.
 if (INSTANCE_UNIX_SOCKET != null) {
   config.addDataSourceProperty("unixSocketPath", INSTANCE_UNIX_SOCKET);
 }
 // [START cloud_sql_postgres_servlet_connect_connector]

 // [END cloud_sql_postgres_servlet_connect_unix]
 // The ipTypes argument can be used to specify a comma delimited list of preferred IP types
 // for connecting to a Cloud SQL instance. The argument ipTypes=PRIVATE will force the
 // SocketFactory to connect with an instance's associated private IP.
 config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
 // [START cloud_sql_postgres_servlet_connect_unix]


 // ... Specify additional connection properties here.
 // [START_EXCLUDE]
 configureConnectionPool(config);
 // [END_EXCLUDE]

 // Initialize the connection pool using the configuration object.
 return new HikariDataSource(config);
}
}
//[END cloud_sql_postgres_servlet_connect_connector]
//[END cloud_sql_postgres_servlet_connect_unix]