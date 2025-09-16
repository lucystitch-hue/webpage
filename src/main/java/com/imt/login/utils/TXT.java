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
package com.imt.login.utils;

import com.imt.login.dto.UserLoginDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TXT {

    public static List<UserLoginDTO> readTxtLogin(String pathFile) {
        List<UserLoginDTO> userLoginDTOList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into username and password
                UserLoginDTO userLoginDTO = new UserLoginDTO();
                String[] parts = line.split("/");
                userLoginDTO.setUsername(parts[0]);
                userLoginDTO.setPassword(parts[1]);
                userLoginDTOList.add(userLoginDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userLoginDTOList;
    }
}
