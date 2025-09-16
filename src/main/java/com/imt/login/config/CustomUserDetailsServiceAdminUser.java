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

import com.imt.login.component.MyService;
import com.imt.login.dto.UserLoginDTO;
import com.imt.login.utils.TXT;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetailsServiceAdminUser implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // get path from env
        ClassPathResource resource = new ClassPathResource(MyService.adminUser());
        String pathFile;
        try {
            pathFile = String.valueOf(resource.getFile().toPath());
            System.out.printf(pathFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserLoginDTO userLoginDTOList = TXT.readTxtLogin(pathFile).stream()
                .filter(userLoginDTO -> userLoginDTO.getUsername().equals(userName)).findAny()
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
        List<String> roleNames = new ArrayList<>();
        roleNames.add("admin");
        List<GrantedAuthority> grantList = roleNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(userLoginDTOList.getUsername(), encryptPassword(userLoginDTOList.getPassword()), grantList);
    }

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
