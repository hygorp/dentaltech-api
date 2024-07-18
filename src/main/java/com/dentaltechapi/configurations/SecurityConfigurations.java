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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "api/user/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,    "api/user/auth/login").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.POST,    "api/user/auth/logout").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/init-reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST,    "api/user/init-reset-password").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/new-password").permitAll()
                        .requestMatchers(HttpMethod.POST,    "api/user/new-password").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST,    "api/user/register").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/token/validation").permitAll()
                        .requestMatchers(HttpMethod.GET,     "api/token/validation").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "api/specialists/all").authenticated()
                        .requestMatchers(HttpMethod.GET,     "api/specialists/**").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "api/specialists/new").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,    "api/specialists/new").hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.OPTIONS, "api/specialist/filter-specialists").authenticated()
                        .requestMatchers(HttpMethod.GET,     "api/specialist/filter-specialists").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "api/specialist/verify-existing-cpf/").authenticated()
                        .requestMatchers(HttpMethod.GET,     "api/specialist/verify-existing-cpf/").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/verify-existing-username").authenticated()
                        .requestMatchers(HttpMethod.GET,     "api/user/verify-existing-username").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "api/user/verify-existing-email").authenticated()
                        .requestMatchers(HttpMethod.GET,     "api/user/verify-existing-email").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "api/specialty/**").authenticated()
                        .requestMatchers(HttpMethod.GET,     "api/specialty/**").authenticated()
                        .requestMatchers(HttpMethod.POST,    "api/specialty/create").authenticated()
                        .requestMatchers(HttpMethod.PUT,     "api/specialty/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE,  "api/specialty/delete-by-id/").hasAuthority("ROLE_ADMIN")

                        .anyRequest().hasAuthority("ROLE_ADMIN")
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
