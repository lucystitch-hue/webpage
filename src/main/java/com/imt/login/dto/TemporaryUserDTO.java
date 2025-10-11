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
package com.imt.login.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class TemporaryUserDTO {
    private String userId;
    private String email;
    private String created;
    private boolean successDelete;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public boolean isSuccessDelete() {
        return successDelete;
    }

    public void setSuccessDelete(boolean successDelete) {
        this.successDelete = successDelete;
    }

    public void setCreated(String created) {
        Instant instant = Instant.parse(created);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Tokyo"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDate = localDateTime.format(formatter);
        this.created = formattedDate;
    }
    public void setCreatedFormat(String created) {

        this.created = created;
    }
}
