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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "A09", schema="TEST3")
public class A09 {
	
    @EmbeddedId
	A09ManagementPK primaryKeys;

    @Column(name = "GRD", columnDefinition = "CHAR(1)")
    private String grd;

    @Column(name = "STARTDATE", columnDefinition = "CHAR(12)")
    private String startDate;

    @Column(name = "ENDDATE", columnDefinition = "CHAR(12)")
    private String endDate;

    @Column(name = "UPDTIMESTAMP")
    private Timestamp updateTimestamp;

    // Constructors, getters, and setters

    public A09(A09ManagementPK primaryKeys, String grd, String startDate, String endDate) {
		super();
		this.primaryKeys = primaryKeys;
		this.grd = grd;
		this.startDate = startDate;
		this.endDate = endDate;
		this.updateTimestamp = Timestamp.from(Instant.now());
	}


    public A09() {
		super();
		// TODO Auto-generated constructor stub
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
}