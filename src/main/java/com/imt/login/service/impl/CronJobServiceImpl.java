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
package com.imt.login.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.imt.login.dto.A08DTO;
import com.imt.login.model.A08A;
import com.imt.login.model.A08B;
import com.imt.login.model.AppNotification;
import com.imt.login.model.UserNotification;
import com.imt.login.model.UserNotificationPK;
import com.imt.login.repository.A08ARepository;
import com.imt.login.repository.A08BRepository;
import com.imt.login.repository.AppUserRepository;
import com.imt.login.repository.NotificationRepository;
import com.imt.login.repository.UserNotificationRepository;
import com.imt.login.service.CronJobService;
import com.imt.login.service.NotificationService;

@Service
public class CronJobServiceImpl implements CronJobService {
    private Logger logger = LoggerFactory.getLogger(CronJobServiceImpl.class);

    @Autowired
    A08ARepository a08ARepository;
    
    @Autowired
    A08BRepository a08BRepository;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    NotificationServiceImpl notificationService;
    
    @Autowired
    NotificationRepository notificationRepository;
    
    @Autowired
    UserNotificationRepository userNotificationRepository;
    
    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public Boolean sendNotification() {
    	try {
        	List<A08A> notifications = a08ARepository.getActiveNotifications();
        	
        	if(notifications.size() == 0) {
            	System.out.println("  --> No auto-notification to exec!");
        		return false;
        	}
        	
        	notifications.forEach(noti -> {
        		System.out.println("A08A - Title: "+ noti.getChgTitle());
        		notificationService.executePushNotification(mapper.map(noti, A08DTO.class), "0", "1");
        	});
        	return true;
    	}catch(Exception e) {
    		System.err.println("  --> Exception: " + e.getMessage());
    		return false;
		}
    }

	@Override
	public Boolean sendNotificationA08B() {
		try {
        	List<A08B> notifications = a08BRepository.getActiveNotifications();
        	List<A08B> saveA08B = notifications;
        	for(A08B a08b: saveA08B) {
        		a08b.setPushFlag("0");
        		a08BRepository.save(a08b);
        	}
        	if(notifications.size() == 0) {
            	System.out.println("  --> No auto-notification A08B to exec!");
        		return false;
        	}
        	// targetConditon = 3 -> auto notice for horse and rider 
        	
        	notifications.forEach(notification -> {
        	    String sendKbn = notification.getSendKbn();
        	    String notificationKbnTopic = sendKbn;
        	    System.out.println("A08B - Title: "+ notification.getChgTitle());

    			String linkUrl = notificationService.buildUrl(sendKbn.substring(0, 2), notification.getLinkUrl());
    			LocalDateTime currentDateTime = LocalDateTime.now();
            	DateTimeFormatter formatterId = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssSSS");
    			FirebaseMessaging fcm = FirebaseMessaging.getInstance();
    			Map<String, String> mapData = new HashMap<>();
        	    mapData.put("MESSAGECODE",currentDateTime.format(formatterId));
    	        mapData.put("TYPE", "1");
    	        mapData.put("CATEGORY", notification.getSendKbn().substring(0, 2));
    	        mapData.put("FILTER", "3");
    	        mapData.put("URL", linkUrl);
    	        // Firebase build
    			Notification notificationBuilder = Notification.builder()
                        .setTitle(notification.getChgTitle())
                        .setBody(notification.getChgText())
                        .build();
    			String topicsString = "'" + notificationKbnTopic + "' in topics";
    			Message msg = Message.builder().setCondition(topicsString).setNotification(notificationBuilder)
						.putAllData(mapData).build();
        	    try {
					fcm.send(msg);
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			    	//Create record on table A05
			    	AppNotification appNotification = new AppNotification();
			        appNotification.setMessageCode(currentDateTime.format(formatterId));
			    	appNotification.setTitle(notification.getChgTitle());
			    	appNotification.setMessage(notification.getChgText());
			    	appNotification.setLinkUrl(notification.getLinkUrl());
			    	appNotification.setExpireDay(notification.getHapyoTime());
			    	appNotification.setSndKbn("1"); //0: Manual 1: Automatic
			    	appNotification.setMkyMd(currentDateTime.format(dateFormatter));
			    	appNotification.setUpdTimestamp(currentDateTime);
					notificationRepository.save(appNotification);
					
					List<String> userIds = new ArrayList<>();
			        	userIds = appUserRepository.getUserIdsToPushNotification("62", "1");
			        
					for (String userId : userIds) {
						UserNotificationPK primaryKeys = new UserNotificationPK();
						primaryKeys.setUserId(userId); // Set the user ID
						primaryKeys.setPushId(appNotification.getMessageCode());
						UserNotification findUserNotification = userNotificationRepository.findById(primaryKeys).orElse(null);

						if(findUserNotification == null){
							UserNotification userNotification = new UserNotification();
							userNotification.setPrimaryKeys(primaryKeys);
							userNotification.setSendDate(currentDateTime.format(formatter));
							userNotification.setStatus("0");
							userNotification.setUpdateTimestamp(currentDateTime);
							userNotificationRepository.save(userNotification);
						}else{

							findUserNotification.setSendDate(currentDateTime.format(formatter));
							findUserNotification.setStatus("0");
							findUserNotification.setUpdateTimestamp(currentDateTime);
							userNotificationRepository.save(findUserNotification);
						}
					}
					
				} catch (FirebaseMessagingException e) {
					System.err.println("  --> Exception: " + e.getMessage());
				}
        	});
        	return true;
    	}catch(Exception e) {
    		System.err.println("  --> Exception: " + e.getMessage());
    		return false;
		}
	}
}
