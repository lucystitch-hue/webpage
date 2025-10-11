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

import java.time.LocalDateTime;

import javax.persistence.Column;

public class AppUserDto {

	private String userId;

	private String sei;

	private String mei;

	private String seiKana;

	private String meiKana;

	private String birthday;

	private String email;

	private String postcode;

	private String sex;

	private String job;

	private String startYear;

	private String userService;

	private String userReason;

	private String userDayOff;
    private String ipatId1;

    private String ipatPars1;

    private String ipatPass1;

    private String ysnFlag1;

    private String ipatId2;

    private String ipatPars2;

    private String ipatPass2;

    private String ysnFlag2;
	
    private String userKbn;


	public AppUserDto(String userId, String sei, String mei, String seiKana, String meiKana, String birthday,
			String email, String postcode, String sex, String job, String startYear, String userService,
			String userReason, String userDayOff, String ipatId1, String ipatPars1, String ipatPass1, String ysnFlag1,
			String ipatId2, String ipatPars2, String ipatPass2, String ysnFlag2, String userKbn) {
		super();
		this.userId = userId;
		this.sei = sei;
		this.mei = mei;
		this.seiKana = seiKana;
		this.meiKana = meiKana;
		this.birthday = birthday;
		this.email = email;
		this.postcode = postcode;
		this.sex = sex;
		this.job = job;
		this.startYear = startYear;
		this.userService = userService;
		this.userReason = userReason;
		this.userDayOff = userDayOff;
		this.ipatId1 = ipatId1;
		this.ipatPars1 = ipatPars1;
		this.ipatPass1 = ipatPass1;
		this.ysnFlag1 = ysnFlag1;
		this.ipatId2 = ipatId2;
		this.ipatPars2 = ipatPars2;
		this.ipatPass2 = ipatPass2;
		this.ysnFlag2 = ysnFlag2;
		this.userKbn = userKbn;
	}

	public String getIpatId1() {
		return ipatId1;
	}

	public void setIpatId1(String ipatId1) {
		this.ipatId1 = ipatId1;
	}

	public String getIpatPars1() {
		return ipatPars1;
	}

	public void setIpatPars1(String ipatPars1) {
		this.ipatPars1 = ipatPars1;
	}

	public String getIpatPass1() {
		return ipatPass1;
	}

	public void setIpatPass1(String ipatPass1) {
		this.ipatPass1 = ipatPass1;
	}

	public String getYsnFlag1() {
		return ysnFlag1;
	}

	public void setYsnFlag1(String ysnFlag1) {
		this.ysnFlag1 = ysnFlag1;
	}

	public String getIpatId2() {
		return ipatId2;
	}

	public void setIpatId2(String ipatId2) {
		this.ipatId2 = ipatId2;
	}

	public String getIpatPars2() {
		return ipatPars2;
	}

	public void setIpatPars2(String ipatPars2) {
		this.ipatPars2 = ipatPars2;
	}

	public String getIpatPass2() {
		return ipatPass2;
	}

	public void setIpatPass2(String ipatPass2) {
		this.ipatPass2 = ipatPass2;
	}

	public String getYsnFlag2() {
		return ysnFlag2;
	}

	public void setYsnFlag2(String ysnFlag2) {
		this.ysnFlag2 = ysnFlag2;
	}

	public AppUserDto() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getSeiKana() {
		return seiKana;
	}

	public void setSeiKana(String seiKana) {
		this.seiKana = seiKana;
	}

	public String getMeiKana() {
		return meiKana;
	}

	public void setMeiKana(String meiKana) {
		this.meiKana = meiKana;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getUserService() {
		return userService;
	}

	public void setUserService(String userService) {
		this.userService = userService;
	}

	public String getUserReason() {
		return userReason;
	}

	public void setUserReason(String userReason) {
		this.userReason = userReason;
	}

	public String getUserDayOff() {
		return userDayOff;
	}

	public void setUserDayOff(String userDayOff) {
		this.userDayOff = userDayOff;
	}

	public String getUserKbn() {
		return userKbn;
	}

	public void setUserKbn(String userKbn) {
		this.userKbn = userKbn;
	}	

}
