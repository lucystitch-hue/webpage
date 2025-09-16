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
package com.imt.login.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyService {
    private static Environment environment;

    @Autowired
    public MyService(Environment environment) {
        MyService.environment = environment;
    }

    public static String adminUser() {
        String path = environment.getProperty("admin.user");
        return path;
    }

    public static String userNotification() {
        String path = environment.getProperty("user.notification");
        return path;
    }
    
    public static String pathCSV() {
        String path = environment.getProperty("csv.log.temporary");
        return path;
    }
}
