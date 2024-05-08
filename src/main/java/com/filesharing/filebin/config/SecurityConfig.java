package com.filesharing.filebin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

// https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-components
// https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#deferred-csrf-token
// The CsrfToken is needed whenever a request is made with an unsafe HTTP method, such as a POST.

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    @Bean
//    JdbcUserDetailsManager users(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        return jdbcUserDetailsManager;
//    }

    @Bean
    JdbcUserDetailsManager users(DataSource dataSource, PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("my_super_secret_password_1234_$%@!"))
                .roles("ADMIN")
                .build();

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        Boolean doesExist = jdbcUserDetailsManager.userExists("admin");

        if(doesExist)
        {
            return jdbcUserDetailsManager;
        }

        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf((csrf) ->
                         csrf.disable()
                )
//                .authorizeRequests(auth -> auth
//                        .anyRequest().authenticated())
//                .authorizeHttpRequests(requests -> requests.requestMatchers("/swagger")
//                                .hasRole("ADMIN").anyRequest()
//                        )
//                .formLogin(Customizer.withDefaults())
                .build();


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
