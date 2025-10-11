/*
 * This file is part of Horse Racing.
 *
 * Copyright (C) [2025] [IMT Solutions]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 */
package com.imt.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class NotificationTemplatesSecurityConfig {
    private static final String LOGIN_URL_2 = "/notification/login";

    private static final String LOGOUT_URL_2 = "/notification/logout";

    @Bean
    public UserDetailsService customerUserDetailsService() {
        return new CustomUserDetailsServiceUserNotification();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider2() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerUserDetailsService());
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider2());
        http.antMatcher("/notification/**")
                .authorizeRequests().anyRequest().hasAnyAuthority("admin","user")
                .and().httpBasic().and()
                .formLogin()
                .loginPage(LOGIN_URL_2)
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/notification/login")
                .defaultSuccessUrl("/notification/notification-management", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl(LOGOUT_URL_2)
                .logoutSuccessUrl(LOGIN_URL_2);
        http.csrf().disable();
        return http.build();
    }
}