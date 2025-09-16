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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "A04", schema="TEST3")
public class UserNotification {
	@EmbeddedId
	UserNotificationPK primaryKeys;
	
//    @Id
//    @Column(name = "USERID", columnDefinition = "CHAR(40)", nullable = false)
//    private String userId;
	
//    @Column(name = "PUSHID", columnDefinition = "CHAR(22)", nullable = false)
//    private String pushId;
	  
	@Column(name = "STATUS", columnDefinition = "CHAR(1)")
	private String status;

	@Column(name = "SENDDATE", columnDefinition = "CHAR(12)")
	private String sendDate;

	@Column(name = "UPDTIMESTAMP")
	private LocalDateTime updateTimestamp;

	public UserNotificationPK getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(UserNotificationPK primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
    
//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}

//	public String getPushId() {
//		return pushId;
//	}
//
//	public void setPushId(String pushId) {
//		this.pushId = pushId;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
}
