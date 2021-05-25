package co.kmacho.config;

import io.micronaut.context.annotation.Factory;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@Factory
public class JdbiContext {

    @Inject
    Datasource datasource;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbiContext.class);

    public Jdbi getJdbi() {
        try {
            Class.forName(datasource.getDriver());
        } catch (ClassNotFoundException e) {
            LOGGER.error("getJdbi: ERROR {}",e.getLocalizedMessage());
        }
        Jdbi jdbi = Jdbi.create(datasource.getUrl(), datasource.getUsername(), datasource.getPassword())
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    public Handle getHandle(){
        Jdbi jdbi = getJdbi();
        return jdbi.open();
    }
}
