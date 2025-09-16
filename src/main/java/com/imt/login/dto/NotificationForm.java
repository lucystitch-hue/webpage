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

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class NotificationForm {
    private String chgTitle;
    private String chgText;
    private String linkUrl;
    private String hapyoTime;
    private String catalog;
    private String condition;
    private Optional<MultipartFile> myFile;

    private String fileName;


    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Optional<MultipartFile> getMyFile() {
        return myFile;
    }

    public void setMyFile(Optional<MultipartFile> myFile) {
        this.myFile = myFile;
    }
}
