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

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;


@Entity
@Table(name = "ASYS01", schema="TEST3")
public class Asys01 {
    @Id
    @Column(name = "SYS01KEYCODE", columnDefinition = "CHAR(4)", nullable = false)
    private String id;

    @Column(name = "SYS01NAME", length = 30)
    private String sys01Name;

    @Column(name = "SYS01VALUE", length = 500)
    private String sys01Value;

    @Column(name = "SYS01MEMO", length = 200)
    private String sys01Memo;

    @Column(name = "UPDTIMESTAMP")
    private Timestamp updateTimestamp;
    

    public Asys01(String id, String sys01Name, String sys01Value, String sys01Memo) {
        this.id = id;
        this.sys01Name = sys01Name;
        this.sys01Value = sys01Value;
        this.sys01Memo = sys01Memo;
        this.updateTimestamp = Timestamp.from(Instant.now());
    }

    public Asys01() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSys01Name() {
        return sys01Name;
    }

    public void setSys01Name(String sys01Name) {
        this.sys01Name = sys01Name;
    }

    public String getSys01Value() {
        return sys01Value;
    }

    public void setSys01Value(String sys01Value) {
        this.sys01Value = sys01Value;
    }

    public String getSys01Memo() {
        return sys01Memo;
    }

    public void setSys01Memo(String sys01Memo) {
        this.sys01Memo = sys01Memo;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
