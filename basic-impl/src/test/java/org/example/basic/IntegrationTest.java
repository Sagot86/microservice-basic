package org.example.basic;

import org.example.basic.config.PostgresInitializerForTests;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Integration test.
 *
 * @author Igor_Orlov
 */
@Inherited
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
@ContextConfiguration(initializers = PostgresInitializerForTests.class)
public @interface IntegrationTest {

}
