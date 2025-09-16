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
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

//@NoArgsConstructor
@Entity
@Table(name = "A05", schema="TEST3")
public class AppNotification {
	@Id
//	@GeneratedValue
    @Column(name = "MESSAGECODE", columnDefinition = "CHAR(22)", nullable = false)
    private String messageCode;

	public AppNotification() {
		this.messageCode = generateId();
	}

	private String generateId() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid.substring(0, 22);
	}

    @Column(name = "TITLE", length = 40)
    private String title;

    @Column(name = "MESSAGE", length = 160)
    private String message;

    @Column(name = "LINKURL", length = 2000)
    private String linkUrl;

    @Column(name = "SNDKBN", columnDefinition = "CHAR(1)")
    private String sndKbn; // Send classification (snd = send, kbn = kubun = classification)

    @Column(name = "MKYMD", columnDefinition = "CHAR(8)")
    private String mkyMd; // Creation date (mky = make/create, ymd = year month day)

    @Column(name = "EXPIREDAY", columnDefinition = "CHAR(12)")
    private String expireDay;

    @Column(name = "UPDTIMESTAMP")
    private LocalDateTime updTimestamp;

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getSndKbn() {
		return sndKbn;
	}

	public void setSndKbn(String sndKbn) {
		this.sndKbn = sndKbn;
	}

	public String getMkyMd() {
		return mkyMd;
	}

	public void setMkyMd(String mkyMd) {
		this.mkyMd = mkyMd;
	}

	public String getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(String expireDay) {
		this.expireDay = expireDay;
	}

	public LocalDateTime getUpdTimestamp() {
		return updTimestamp;
	}

	public void setUpdTimestamp(LocalDateTime updTimestamp) {
		this.updTimestamp = updTimestamp;
	}
}
