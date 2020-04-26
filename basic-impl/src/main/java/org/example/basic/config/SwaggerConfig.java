package org.example.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig.
 *
 * @author Igor_Orlov
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    protected static final String[] SECURITY_ANT_MATCHERS = {"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**"};

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.basic"))
                .paths(PathSelectors.any())
                .build();
    }
}
