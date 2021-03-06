package de.medhelfer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.inject.Inject;

@EnableWebSecurity
public class SpringSecurityConfig {

    @Configuration
    @Order(1)
    @EnableGlobalMethodSecurity(securedEnabled = true)
    public static class JwsSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Inject
        private StatelessAuthenticationFilter statelessAuthenticationFilter;

        // TODO why is it necessary to ignore all requests in order to avoid duplicate calls of statelessAuthenticationFilter?
        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/login").anonymous()
                    .and().addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return authenticationManager();
        }
    }
}
