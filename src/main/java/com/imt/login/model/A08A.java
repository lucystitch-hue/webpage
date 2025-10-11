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
@Table(name = "A08A",schema="TEST3")
public class A08A {
    @Id
    @Column(name = "CHGNUM", length = 3, nullable = false)
    private String chgNum;

    @Column(name = "SENDKBN", length = 2)
    private String sendKbn;

    @Column(name = "CHGTITLE", length = 30)
    private String chgTitle;

    @Column(name = "CHGTEXT", length = 255)
    private String chgText;

    @Column(name = "PUSHFLG", length = 1)
    private String pushFlag;

    @Column(name = "LINKURL", length = 40)
    private String linkUrl;

    @Column(name = "HAPYO_TIME", length = 12)
    private String hapyoTime;

    @Column(name = "UPDTIMESTAMP")
    private Timestamp updateTimestamp;

    public A08A(String chgNum, String sendKbn, String chgTitle, String chgText, String pushFlag, String hapyoTime, String linkUrl, Timestamp updateTimestamp) {
        this.chgNum = chgNum;
        this.sendKbn = sendKbn;
        this.chgTitle = chgTitle;
        this.chgText = chgText;
        this.pushFlag = pushFlag;
        this.hapyoTime = hapyoTime;
        this.linkUrl = linkUrl;
        this.updateTimestamp = updateTimestamp;
    }

    public A08A() {
    }

    public String getChgNum() {
        return chgNum;
    }

    public void setChgNum(String chgNum) {
        this.chgNum = chgNum;
    }

    public String getSendKbn() {
        return sendKbn;
    }

    public void setSendKbn(String sendKbn) {
        this.sendKbn = sendKbn;
    }

    public String getChgTitle() {
        return chgTitle;
    }

    public void setChgTitle(String chgTitle) {
        this.chgTitle = chgTitle;
    }

    public String getChgText() {
        return chgText;
    }

    public void setChgText(String chgText) {
        this.chgText = chgText;
    }

    public String getPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getHapyoTime() {
        return hapyoTime;
    }

    public void setHapyoTime(String hapyoTime) {
        this.hapyoTime = hapyoTime;
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
