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
package com.imt.login.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "A07", schema="TEST3")
public class TokenManagement {
	@EmbeddedId
	TokenManagementPK primaryKeys;
	
//    @Id
//    @Column(name = "USERID", columnDefinition = "CHAR(40)", nullable = false)
//    @NonNull
//    private String userId;
//
//    @Column(name = "TKNNUM", columnDefinition = "CHAR(1)")
//    @NonNull
//    private String tknNum;

    @Column(name = "UTOKEN", length = 4096, nullable = false)
    @NonNull
    private String uToken;

    @Column(name = "UPDTIMESTAMP")
    private LocalDateTime updTimestamp;

//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//
//	public String getTknNum() {
//		return tknNum;
//	}
//
//	public void setTknNum(String tknNum) {
//		this.tknNum = tknNum;
//	}

	public String getuToken() {
		return uToken;
	}

	public void setuToken(String uToken) {
		this.uToken = uToken;
	}

	public TokenManagementPK getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(TokenManagementPK primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

	public LocalDateTime getUpdTimestamp() {
		return updTimestamp;
	}

	public void setUpdTimestamp(LocalDateTime updTimestamp) {
		this.updTimestamp = updTimestamp;
	}
    
}
