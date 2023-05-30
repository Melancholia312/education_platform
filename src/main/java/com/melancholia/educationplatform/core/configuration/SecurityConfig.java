package com.melancholia.educationplatform.core.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    /*private final AppOAuth2UserService appOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                formLogin()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(appOAuth2UserService)
                .and()
                .successHandler((request, response, authentication) -> {
                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                    appOAuth2UserService.processOAuthPostLogin(
                            oauthUser.getEmail(),
                            oauthUser.getFullName(),
                            oauthUser.getPhoneNumber());
                    response.sendRedirect("/my-courses");
                });
        return http.build();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin();
        return http.build();
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}