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

import java.sql.Timestamp;
import java.util.Base64;

import com.imt.login.model.A09;
import com.imt.login.model.A09ManagementPK;

public class A09DTO {

	A09ManagementPK primaryKeys;

    private String grd;

    private String startDate;

    private String endDate;

    private Timestamp updateTimestamp;
    
    private String raceName;
    
    public static A09DTO convertToDTO(A09 a09) {
        A09DTO dto = new A09DTO(); 
        dto.setEndDate(a09.getEndDate());
        dto.setGrd(a09.getGrd());
        dto.setPrimaryKeys(a09.getPrimaryKeys());
        dto.setRaceName(Base64.getEncoder().encodeToString(a09.getPrimaryKeys().getRaceName().getBytes()).replace("/", "slash"));
        dto.setStartDate(a09.getStartDate());
        dto.setUpdateTimestamp(a09.getUpdateTimestamp());
        return dto;
    }

	public A09ManagementPK getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(A09ManagementPK primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getGrd() {
		return grd;
	}

	public void setGrd(String grd) {
		this.grd = grd;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
    
}
