package com.iot.relay.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    
        private final JwtAuthenticationEntryPoint unauthorizedHandler;
    
        private final JwtRequestFilter authenticationJwtTokenFilter;
    
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
    
        @Bean
        @Profile(value = {"!test"})
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                     .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests()
                    .antMatchers("/authenticate", "/create/user","/v3/*","/swagger-ui/*").permitAll()
                    .anyRequest().authenticated();
    
            http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    
            return http.build();
        }

        @Bean
        @Profile(value = {"test"})
        public SecurityFilterChain filterChainTest(HttpSecurity http) throws Exception {
            http.csrf().disable()
            // Permit all requests without authentication
            .authorizeRequests().anyRequest().permitAll();
    
            return http.build();
        }
    }