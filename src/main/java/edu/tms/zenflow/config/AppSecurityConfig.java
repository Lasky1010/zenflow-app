package edu.tms.zenflow.config;

import edu.tms.zenflow.security.JwtTokenValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class AppSecurityConfig {


    private final JwtTokenValidationFilter jwtTokenValidationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/reg", "/auth")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/private").authenticated())
                .addFilterBefore(jwtTokenValidationFilter, LogoutFilter.class);

        return http.build();
    }

}
