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

import javax.persistence.Column;

public class A08DTO {
	private String chgNum;
    private String sendKbn;
    private String chgTitle;
    private String chgText;
    private String pushFlag;
    private String linkUrl;
    private String hapyoTime;
    private Timestamp updateTimestamp;
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
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getHapyoTime() {
		return hapyoTime;
	}
	public void setHapyoTime(String hapyoTime) {
		this.hapyoTime = hapyoTime;
	}
	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
    
}
