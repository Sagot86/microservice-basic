package org.example.basic.config;

import static org.example.basic.config.SwaggerConfig.SECURITY_ANT_MATCHERS;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig.
 *
 * @author Igor_Orlov
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(SECURITY_ANT_MATCHERS)
                .permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .csrf().disable();
    }
}
