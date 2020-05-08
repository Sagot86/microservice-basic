package org.example.basic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

/**
 * PostgresInitializerForTests.
 *
 * @author Igor_Orlov
 */
@Slf4j
public class PostgresInitializerForTests implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final GenericContainer<?> POSTGRES;
    private static final String JDBC_URL;

    static {
        POSTGRES = new GenericContainer<>("postgres:10.1")
                .withExposedPorts(5432)
                .withEnv("POSTGRES_DB", "tdb")
                .withEnv("POSTGRES_USER", "postgres")
                .withEnv("POSTGRES_PASSWORD", "postgres")
                .withClasspathResourceMapping("db/00_create_db.sql", "/docker-entrypoint-initdb.d/00_create_db.sql", BindMode.READ_ONLY);

        POSTGRES.start();
        Runtime.getRuntime().addShutdownHook(new Thread(POSTGRES::stop));
        JDBC_URL = String.format("jdbc:postgresql://%s:%d/tdb?currentSchema=basic", POSTGRES.getContainerIpAddress(), POSTGRES.getFirstMappedPort());
        log.info("Postgres container started. (" + JDBC_URL + ")");
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(springApplicationProperties()).applyTo(applicationContext.getEnvironment());
    }

    private static String[] springApplicationProperties() {
        return new String[]{
                "spring.datasource.url=" + JDBC_URL,
                "spring.datasource.username=basic",
                "spring.datasource.password=basic",
                "liquibase.enabled=true",
                "liquibase.url=" + JDBC_URL,
                "liquibase.user=basic",
                "liquibase.password=basic"
        };
    }
}