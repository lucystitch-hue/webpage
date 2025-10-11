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

public class FcmTokenUserDTO {

	private String userId;
	private String pushThisWeek;
	private String pushNews;
	private String userKbn;

	public FcmTokenUserDTO(String userId, String pushThisWeek, String pushNews, String userKbn) {
		super();
		this.userId = userId;
		this.pushThisWeek = pushThisWeek;
		this.pushNews = pushNews;
		this.userKbn = userKbn;
	}

	public FcmTokenUserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String setPushThisWeek() {
		return pushThisWeek;
	}

	public void setPushThisWeek(String pushThisWeek) {
		this.pushThisWeek = pushThisWeek;
	}

	public String getPushNews() {
		return pushNews;
	}

	public void setPushNews(String pushNews) {
		this.pushNews = pushNews;
	}

	public String getUserKbn() {
		return userKbn;
	}

	public void setUserKbn(String userKbn) {
		this.userKbn = userKbn;
	}
}