package Lab4Service.configs;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@DataSourceDefinition(
        className = "org.postgresql.ds.PGSimpleDataSource",
        name = "java:global/jdbc/lab4-data-source",
        user = "app",
        password = "qwerty",
        databaseName = "application_db",
        properties = {"connectionAttributes=;create=true"}
)
public class DatabaseConfig {
}
