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
/*     Summary of file  to comment on:
 *     Modified date:  13/12/2023
 *     Corresponding : slide 3,4
 *     Description of change:
 *      -  + update slide 3, 4(hanlde page urgent notice)
 */
package com.imt.login.service;

import com.imt.login.dto.A08DTO;
import com.imt.login.model.A08A;
import com.imt.login.model.AppNotification;
import com.imt.login.model.Asys01;
import com.imt.login.model.Asys02;
import com.imt.login.model.Template;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

public interface NotificationService {
    List<Template> listAll();

    AppNotification findById(String id);

    Template findByIdTemplate(String id);

    A08A createNotification(String chgTitle, String chgText, String linkUrl, String hapyoTime);

    Template addTemplate(String title, String message, String linkURL);

    Template createTemplate(Template template);

    Boolean sendNotification(String chgTitle, String chgText, String linkUrl, String hapyoTime, String catalog, String condition, String fileName);

    void delete(String templateId);

    Template edit(String id, Template template);

    Boolean executePushNotificationWithCSV(A08A a08aRecord, String contentFile);
    Boolean executePushNotification(A08DTO a08Record, String targetCondition, String isAuto);

    Boolean checkTemplate();

	Object addUrgentNotice(String title, String info, String hyojiStart, String hyojiEnd, Optional<Boolean> yukoFlg);

	void createUrgentNotice(@Valid Asys02 asys02);
	Asys02 findById();

	Asys01 findByIdSystem(String keycode);

	void updateSystemConstant(String name, String value, String memo, String keycode);

	Asys01 confirmSystemConstant(String name, String value, String memo, String keycode);

	void deleteSystemConstant(String keycode);
	
	public String buildUrl(String category, String urlA08a);
	
}
