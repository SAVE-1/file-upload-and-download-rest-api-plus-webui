package com.filesharing.filebin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-components
// https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#deferred-csrf-token
// The CsrfToken is needed whenever a request is made with an unsafe HTTP method, such as a POST.

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) ->
                csrf.disable()
        );
        return http.build();
    }

}
