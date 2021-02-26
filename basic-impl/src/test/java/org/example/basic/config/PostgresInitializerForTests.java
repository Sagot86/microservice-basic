package org.example.basic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

/**
 * PostgresInitializerForTests.
 *
 * @author Igor_Orlov
 */
@Slf4j
public class PostgresInitializerForTests implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:11")
                .withCopyFileToContainer(MountableFile.forClasspathResource("db/00_create_db.sql"), "/docker-entrypoint-initdb.d/init.sql");
        POSTGRES.start();
        log.info("Postgres container started. URL (" + POSTGRES.getJdbcUrl() + ")");
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                "spring.datasource.username=" + POSTGRES.getUsername(),
                "spring.datasource.password=" + POSTGRES.getPassword(),
                "spring.liquibase.enabled=true",
                "spring.liquibase.url=" + POSTGRES.getJdbcUrl(),
                "spring.liquibase.user=" + POSTGRES.getUsername(),
                "spring.liquibase.password=" + POSTGRES.getPassword()
        ).applyTo(applicationContext.getEnvironment());
    }
}