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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class A09ManagementPK implements Serializable{
	@Column(name = "KAISAIYMD", columnDefinition = "CHAR(8)")
    private String kaisaiYmd; // Race holding date (kaisai = holding/hosting, ymd = year month day)
	@Column(name = "RACENAME", length = 20)
    private String raceName;
	
	
	public A09ManagementPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	public A09ManagementPK(String kaisaiYmd, String raceName) {
		super();
		this.kaisaiYmd = kaisaiYmd;
		this.raceName = raceName;
	}
	public String getKaisaiYmd() {
		return kaisaiYmd;
	}
	public void setKaisaiYmd(String kaisaiYmd) {
		this.kaisaiYmd = kaisaiYmd;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	
	
}
