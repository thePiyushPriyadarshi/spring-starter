package com.thePiyushPriyadarshi.springStarter.config;

import com.thePiyushPriyadarshi.springStarter.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] PUBLIC_ROUTES = {"/auth/**","/swagger-ui/**","/v3/api-docs"};
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.formLogin(Customizer.withDefaults()); -> this is for default login

          httpSecurity
                  .csrf(AbstractHttpConfigurer::disable)
                  .authorizeHttpRequests(auth -> auth
                                  .requestMatchers(PUBLIC_ROUTES).permitAll()
                                  .requestMatchers("/auth/protected").authenticated()
                                  .anyRequest().authenticated())
                  .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
