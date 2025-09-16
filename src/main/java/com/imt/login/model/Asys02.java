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

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "ASYS02", schema="TEST3")
public class Asys02 {
    @Id
    @Column(name = "ID", columnDefinition = "CHAR(1)", nullable = false)
    private String id;

    @Column(name = "CAPTION", length = 100)
    private String caption;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "INFO", length = 4000)
    private String info;

    @Column(name = "HYOJI_START", columnDefinition = "CHAR(12)")
    private String hyojiStart; // Display start time (hyoji = display/show in Japanese)

    @Column(name = "HYOJI_END", columnDefinition = "CHAR(12)")
    private String hyojiEnd; // Display end time (hyoji = display/show in Japanese)

    @Column(name = "YUKO_FLG", columnDefinition = "CHAR(1)")
    private String yukoFlg; // Valid flag (yuko = valid/effective in Japanese)

    @Column(name = "UPDTIMESTAMP")
    private Timestamp updateTimestamp;

    public Asys02(String id, String caption, String title, String info, String hyojiStart, String hyojiEnd, String yukoFlg) {
        this.id = id;
        this.caption = caption;
        this.title = title;
        this.info = info;
        this.hyojiStart = hyojiStart;
        this.hyojiEnd = hyojiEnd;
        this.yukoFlg = yukoFlg;
        this.updateTimestamp = Timestamp.from(Instant.now());
    }

    public Asys02() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHyojiStart() {
        return hyojiStart;
    }

    public void setHyojiStart(String hyojiStart) {
        this.hyojiStart = hyojiStart;
    }

    public String getHyojiEnd() {
        return hyojiEnd;
    }

    public void setHyojiEnd(String hyojiEnd) {
        this.hyojiEnd = hyojiEnd;
    }

    public String getYukoFlg() {
        return yukoFlg;
    }

    public void setYukoFlg(String yukoFlg) {
        this.yukoFlg = yukoFlg;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
