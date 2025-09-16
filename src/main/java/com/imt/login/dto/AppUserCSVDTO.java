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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class AppUserCSVDTO {

    private String userId;
    private String sei;
    private String mei;
    private String email;
    private String initialYmd;
    private LocalDateTime updateTimestamp;
    private String latestLogin;
    
    public AppUserCSVDTO() {
    }
    
    public AppUserCSVDTO(String userId, String sei, String mei, String email, String initialYmd, LocalDateTime updateTimestamp) {
        this.userId = userId;
        this.sei = sei;
        this.mei = mei;
        this.email = email;
        this.initialYmd = initialYmd;
        this.updateTimestamp = updateTimestamp;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSei() {
        return sei;
    }

    public void setSei(String sei) {
        this.sei = sei;
    }

    public String getMei() {
        return mei;
    }

    public void setMei(String mei) {
        this.mei = mei;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInitialYmd() {
        return initialYmd;
    }

    public void setInitialYmd(String initialYmd) {
        this.initialYmd = initialYmd;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getLatestLogin() {
        return latestLogin;
    }

    public void setLatestLogin(String latestLogin) {
        this.latestLogin = latestLogin;
    }
}
