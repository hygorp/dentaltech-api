package com.dentaltechapi.configurations;

import com.dentaltechapi.components.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "api/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/user/login").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/user/logout").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/recovery").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/user/recovery").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/recovery-validation").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/user/recovery-validation").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/user/register").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
