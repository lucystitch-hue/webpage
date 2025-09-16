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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "A06", schema="TEST3")
public class Template {
    @Id
    @Column(name = "UNYONUM",length = 3)
    private String unyonum;

    @Column(name = "TITLEFORMAT", length = 40)
    private String titleFormat;

    @Column(name = "TEIKEIFORMAT", length = 160)
    private String teikeiFormat; // Standard format template (teikei = standard/fixed format)

    @Column(name = "LINKURL", length = 2000)
    private String linkUrl;

    @Column(name = "UPDTIMESTAMP")
    private Timestamp updateTimestamp;

    public Template() {
    }

    public Template(String unyonum, String titleFormat, String teikeiFormat, String linkUrl, Timestamp updateTimestamp) {
        this.unyonum = unyonum;
        this.titleFormat = titleFormat;
        this.teikeiFormat = teikeiFormat;
        this.linkUrl = linkUrl;
        this.updateTimestamp = updateTimestamp;
    }

    public String getUnyonum() {
        return unyonum;
    }

    public void setUnyonum(String unyonum) {
        this.unyonum = unyonum;
    }

    public String getTitleFormat() {
        return titleFormat;
    }

    public void setTitleFormat(String titleFormat) {
        this.titleFormat = titleFormat;
    }

    public String getTeikeiFormat() {
        return teikeiFormat;
    }

    public void setTeikeiFormat(String teikeiFormat) {
        this.teikeiFormat = teikeiFormat;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
