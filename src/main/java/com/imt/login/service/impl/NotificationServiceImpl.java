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
 *     Corresponding : slide 5
 *     Description of change:
 *      + update slide 5 (fix error 500)
 */
package com.imt.login.service.impl;

import com.google.firebase.messaging.*;
import com.imt.login.dto.A08DTO;
import com.imt.login.dto.FcmTokenUserDTO;
import com.imt.login.model.*;
import com.imt.login.repository.*;
import com.imt.login.service.NotificationService;
import com.imt.login.utils.CSV;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.validation.Valid;

@Service
public class NotificationServiceImpl implements NotificationService {


    private Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserNotificationRepository userNotificationRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    A08ARepository a08ARepository;
    
    @Autowired
    A08BRepository a08BRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    Asys02Repository asys02Repository;
    
    @Autowired
    Asys01Repository asys01Repository;

    @Override
    public List<Template> listAll() {
        return templateRepository.findAll();
    }
    public <T> T getLastElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null; // or throw an exception if you prefer
        }

        return list.get(list.size() - 1);
    }

    // generate Id A05
//    public static String generateIdAppNotification(){
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssSSS");
//        return LocalDateTime.now().format(formatter);
//    }

    public String generateIdA08a() {

        List<A08A> listAll = a08ARepository.findAll();
        Long id;
        if (listAll.size() == 0) {
            id = 1L;
        } else {
            A08A a08A = getLastElement(listAll);
            id = Long.valueOf(a08A.getChgNum()) + 1;
        }
        return String.format("%03d", id);
    }
    
    public String generateIdTemplate() {

        List<Template> listAll = templateRepository.findAllGenID();
		Integer n = 1;

		for (Template a06 : listAll) {
			int unyonum = Integer.valueOf(a06.getUnyonum().trim());

			if(unyonum == n) {
				n += 1;
			}else if (unyonum > n) {
				break;
			}
		}
        return String.format("%03d", n);
    }

    @Override
    public AppNotification findById(String id) {
        return notificationRepository.findById(id).get();
    }

    @Override
    public Template findByIdTemplate(String id) {
        return templateRepository.findById(id).get();
    }

    @Override
    public A08A createNotification(String chgTitle, String chgText, String linkUrl, String hapyoTime) {
    	LocalDateTime timestamp = LocalDateTime.parse(hapyoTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String convertedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        A08A newNotification = new A08A();
        newNotification.setChgNum(generateIdA08a());
        newNotification.setHapyoTime(convertedTimestamp);
        newNotification.setChgTitle(chgTitle);
        newNotification.setChgText(chgText);
        newNotification.setLinkUrl(linkUrl);
        newNotification.setUpdateTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return newNotification;
    }

    @Override
    public Template addTemplate(String title, String message, String linkURL) {
        Template confirmTemplate = new Template();
        confirmTemplate.setTitleFormat(title);
        confirmTemplate.setLinkUrl(linkURL);
        confirmTemplate.setTeikeiFormat(message);
        return confirmTemplate;
    }
    @Override
    public Template createTemplate(Template template) {
        Template newTemplate = new Template();
        newTemplate.setTitleFormat(template.getTitleFormat());
        newTemplate.setLinkUrl(template.getLinkUrl());
        newTemplate.setTeikeiFormat(template.getTeikeiFormat());
        newTemplate.setUnyonum(generateIdTemplate());
        newTemplate.setUpdateTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return templateRepository.save(newTemplate) ;
    }

    public A08A createA08a(String chgTitle, String chgText, String linkUrl, String hapyoTimeStr, String categoryCondition){
        A08A noti = new A08A();
        noti.setChgNum(generateIdA08a());
        noti.setChgTitle(chgTitle);
        noti.setChgText(chgText);
        noti.setLinkUrl(linkUrl);
        noti.setHapyoTime(hapyoTimeStr);
        noti.setSendKbn(categoryCondition);
        noti.setUpdateTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        noti.setPushFlag("1");
        return noti;
    }

//    public void dropA07(String userId, String uToken){
//        TokenManagement a07 = tokenManagementRepository.findByuTokenAndUserId(userId,uToken);
//        if(a07 != null){
//            tokenManagementRepository.delete(a07);
//        }
//    }


    @Override
    public Boolean sendNotification(String chgTitle, String chgText, String linkUrl, String hapyoTime, String categoryCondition, String targetCondition, String fileName) {
    	try {
	    	System.out.println("hapyoTime");
	    	System.out.println(hapyoTime);
	    	// Convert hapyoTime to yyyyMMddHHmm format
	        LocalDateTime hapyoDateTime = LocalDateTime.parse(hapyoTime, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
	        String hapyoTimeStr = hapyoDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

	        // categoryCondition
	        if(StringUtils.isBlank(categoryCondition)) {
	        	categoryCondition = "62";
	    	}

	    	// targetCondition: 0: All users, 1: Test users, 2: CSV. Default: 0
	    	if(StringUtils.isBlank(targetCondition)) {
	    		targetCondition = "0";
	    	}

	        if(targetCondition.equals("2") && StringUtils.isBlank(fileName)){
	            return false;
	        }

	        //Create record on table A08A (Shirai confirm do not use A08A for this function)
	    	A08A noti = createA08a(chgTitle, chgText, linkUrl, hapyoTimeStr, categoryCondition);

	    	if(targetCondition.equals("2")) {
	    		return executePushNotificationWithCSV(noti, fileName);
	    	}else {
	    		return executePushNotification(mapper.map(noti, A08DTO.class), targetCondition, "0");
	    	}
		}
		catch(Exception e) {
    		System.err.println("Exception: " + e.getMessage());
    		return false;
		}
    }

    @Override
    public void delete(String notificationId) {
    	try {
    		templateRepository.deleteById(notificationId);
    	}
		catch(Exception e) {
    		System.err.println("Exception: " + e.getMessage());
		}
    }

    @Override
    public Template edit(String id, Template template) {
        Template templateFind = templateRepository.findById(id).get();
        templateFind.setTitleFormat(template.getTitleFormat());
        templateFind.setLinkUrl(template.getLinkUrl());
        templateFind.setTeikeiFormat(template.getTeikeiFormat());
        templateFind.setUpdateTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return templateRepository.save(templateFind);
    }

    @Override
    public Boolean executePushNotificationWithCSV(A08A a08aRecord, String fileName) {
    	System.out.println("executePushNotificationWithCSV");
        
        try {
	    	String targetCondition = "2"; // 2 = CSV
	    	String isAuto = "0"; //0: Manual 1: Automatic
	        List<String> csvUserIds = new ArrayList<>() ;
	        
			if (StringUtils.isBlank(fileName)) {
	        	return true;
			}
	
	        csvUserIds = CSV.readCSVContent();
	
	        if(csvUserIds.isEmpty()) {
	        	return true;
	        }
	        
	        // Get users by filters
	        String category = a08aRecord.getSendKbn();
	        
    		FirebaseMessaging fcm = FirebaseMessaging.getInstance();
            Map<String, String> mapData = new HashMap<>();
        	LocalDateTime currentDateTime = LocalDateTime.now();
        	DateTimeFormatter formatterId = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssSSS");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

	    	//Create record on table A05
	    	AppNotification appNotification = new AppNotification();
	        appNotification.setMessageCode(currentDateTime.format(formatterId));
	    	appNotification.setTitle(a08aRecord.getChgTitle());
	    	appNotification.setMessage(a08aRecord.getChgText());
	    	appNotification.setLinkUrl(a08aRecord.getLinkUrl());
	    	appNotification.setExpireDay(a08aRecord.getHapyoTime());
	    	appNotification.setSndKbn(isAuto);
	    	appNotification.setMkyMd(currentDateTime.format(dateFormatter));
	    	appNotification.setUpdTimestamp(currentDateTime);
	    	notificationRepository.save(appNotification);
	        mapData.put("MESSAGECODE", appNotification.getMessageCode());
	        mapData.put("TYPE", isAuto);
	        mapData.put("CATEGORY", a08aRecord.getSendKbn());
	        mapData.put("FILTER", targetCondition);
	        mapData.put("URL", appNotification.getLinkUrl());
	        System.out.println("---executePushNotification");
	        // Get Push Token & send notification to devices

			List<UserNotification> a04List = new ArrayList<>();
			List<UserNotification> a04ListDrop = new ArrayList<>();
			List<String> pushUser = new ArrayList<>();
				for(String userId : csvUserIds) {
					String userIdCSV = null;
					if(category.equals("62")) {
						userIdCSV = appUserRepository.checkUserIdCSV(userId.replace("﻿", "")+"        ");
					}else {
						userIdCSV = appUserRepository.checkUserIdsToPushNotification(category, targetCondition, userId.replace("﻿", "")+"        ");
					}
					if(userIdCSV!=null) {
						pushUser.add(userIdCSV.trim());
						UserNotificationPK primaryKeys = new UserNotificationPK();
						primaryKeys.setUserId(userId); // Set the user ID
						primaryKeys.setPushId(appNotification.getMessageCode());

						UserNotification findUserNotification = userNotificationRepository.findById(primaryKeys).orElse(null);
						if(findUserNotification == null){
							//create

							UserNotification userNotification = new UserNotification();
							userNotification.setPrimaryKeys(primaryKeys);
							userNotification.setSendDate(currentDateTime.format(formatter));
							userNotification.setStatus("0");
							userNotification.setUpdateTimestamp(currentDateTime);
							a04List.add(userNotification);
						}else{
							//update
							findUserNotification.setSendDate(currentDateTime.format(formatter));
							findUserNotification.setStatus("0");
							findUserNotification.setUpdateTimestamp(currentDateTime);
							a04List.add(findUserNotification);
							a04ListDrop.add(findUserNotification);
						}
					}
		        }

	        if(pushUser.isEmpty() || a04List.isEmpty()) {
	        	return true;
	        }

	        // Firebase build
	        Notification notificationBuilder = Notification.builder()
	                .setTitle(appNotification.getTitle())
	                .setBody(appNotification.getMessage())
	                .build();

	        try {
	            int batchSize = 5; // Set batch size
	            List<String> batchTopics = new ArrayList<>();
	            for (int i = 0; i < pushUser.size(); i++) {
	                String userId = pushUser.get(i).trim();
	                batchTopics.add("'" + userId + "'" + " in topics");
	                
	                // If batch is full or reached the end of the list, send the message
	                if (batchTopics.size() == batchSize || i == pushUser.size() - 1) {
	                    String topicsString = String.join(" || ", batchTopics);
	                    Message msg = Message.builder()
	                            .setCondition(topicsString)
	                            .setNotification(notificationBuilder)
	                            .putAllData(mapData)
	                            .build();

	                    fcm.send(msg);
	                    
	                    // Clear the batch topics for the next iteration
	                    batchTopics.clear();
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Push Notification Error: " + e.getMessage());
	        }
			System.out.printf("---------------------------------");
			if(a04ListDrop.size()!=0){
				userNotificationRepository.deleteAll(a04ListDrop);
			}
	        userNotificationRepository.saveAll(a04List);
			System.out.printf("---------------------------------");
	        return true;
        }
		catch(Exception e) {
    		System.err.println("Exception--------: " + e.getMessage());
	        return false;
		}
    }
    
    public static List<String> hasCommonElements(List<FcmTokenUserDTO> list1, List<String> list2) {
        List<String> listUser= new ArrayList<>();
    	
    	
    	for(FcmTokenUserDTO hexString1 : list1) {
            byte[] byteArray1 = hexStringToByteArray(hexString1.getUserId().trim());
            for(String hexString2 : list2) {
                byte[] byteArray2 = hexStringToByteArray(hexString2);
                if(Arrays.equals(byteArray1, byteArray2)) {
                	listUser.add(hexString1.getUserId());
                }
            }
        }
        return listUser;
    }

	public String buildUrl(String category, String urlA08a){
		// build url with format:
		//                     {0} is yellowLetter ,
		//                     {1} is blueLetter,
		//                     {2} is redLetter
		// eg: https://localhost/TESTDB/accessD.html?CNAME=sw01dde1004202303070120230902/4F
		String linkUrl = "https://localhost/TESTDB/access{0}.html?CNAME={1}{2}";
		String yellowLetter="";
		String blueLetter = urlA08a;
		String redLetter = CheckSum.calculate(urlA08a);
		// prioritize define 5 case 50, 51 ,52 ,53 ,54
		switch (category) {
			case "61":
				yellowLetter = "T";
				break;
			case "62":
				yellowLetter = "D";
				break;
			case "63":
				yellowLetter = "S";
				break;
			case "64":
				yellowLetter = "K";
				break;
			case "50":
				yellowLetter = "T";
				break;
			case "54":
				yellowLetter = "S";
				break;
			case "51":
			case "52":
			case "53":
				yellowLetter = "D";
				break;
			default:
				return urlA08a;
		}

		// format url
		return MessageFormat.format(linkUrl,yellowLetter,blueLetter,redLetter);
	}

    @Override
    public Boolean executePushNotification(A08DTO a08Record, String targetCondition, String isAuto) {
        try {
	        // 2 = CSV
	        if(targetCondition.equals("2")) {
	        	return true;
	        }
	        // Get Push Token & filtered users to send notification to devices
	        String category = a08Record.getSendKbn();
	        if(category.equals("62") && targetCondition.equals("1")) {
	        	category= "EMERGENCYTOPIC";
	        }
	        List<String> userIds = new ArrayList<>();
	        if(!targetCondition.equals("3")) {
	        	userIds = appUserRepository.getUserIdsToPushNotification(category, targetCondition);

		        if(userIds.isEmpty()) {
					return true;
				}
	        }
	       if(targetCondition.equals("0") && category.equals("62")) {
	    	   userIds = appUserRepository.getAllUserActive();

		        if(userIds.isEmpty()) {
					return true;
				}
	       }
	        
			String linkUrl=a08Record.getLinkUrl();
			// check notice auto
			if (isAuto.equals("1")) {
				//build url
				linkUrl = buildUrl(category.substring(0, 2), a08Record.getLinkUrl());
			}
			LocalDateTime currentDateTime = LocalDateTime.now();
        	DateTimeFormatter formatterId = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssSSS");
			FirebaseMessaging fcm = FirebaseMessaging.getInstance();
			Map<String, String> mapData = new HashMap<>();
    	    mapData.put("MESSAGECODE", currentDateTime.format(formatterId));
	        mapData.put("TYPE", isAuto);
	        mapData.put("CATEGORY", a08Record.getSendKbn().substring(0, 2));
	        mapData.put("FILTER", targetCondition);
	        mapData.put("URL", linkUrl);
	        // Firebase build
			Notification notificationBuilder = Notification.builder()
                    .setTitle(a08Record.getChgTitle())
                    .setBody(a08Record.getChgText())
                    .build();

 	        // Send Notification.
//			TargetCondition
//			9: Test users - A01.USERKBN
//			Category format ID:description - Topic Name
//			10:Race Cancellation - PUSHTORIKESHI
//			11:Race Exclusion - PUSHJOGAI
//			12:Jockey Change - PUSHKISYU
//			13:Start Time Change - PUSHJIKOKU
//			19:Track Condition Change - PUSHBABA
//			20:Weather Change - PUSHWEATHER
//			50:GI Registration Horse (GI Race Registration Horse) - PUSHUTOKUBETSU
//			51:GI Starting Horse (GI Race Starting Horse Confirmed) - PUSHMOKUDEN
//			52:GI Post Position Determined (GI Race Post Position Confirmed) - PUSHYOKUDEN
//			53:GI Horse Weight (GI Race Horse Weight) - PUSHUMAWEIGHT
//			54:GI Payout (GI Race Payout) - PUSHHARAI
//			90:This Week's Highlights - PUSHTHISWEEK
//			91:Special Information - PUSHNEWS
//			62: Emergency Notification - EMERGENCYTOPIC
			String topic = "EMERGENCYTOPIC";
			String topicTest = "EMERGENCYTOPIC";
			switch ( category.substring(0, 2) ) {
			    case "10": {
					if (targetCondition.equals("1")) {
						topicTest = "PUSHTORIKESHITEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHTORIKESHITEST";
						topic = "PUSHTORIKESHI";
					}
					break;
				}

			    case "11":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHJOGAITEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHJOGAITEST";
						topic = "PUSHJOGAI";
					}
					break;
				}
			    case "12":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHKISYUTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHKISYUTEST";
						topic = "PUSHKISYU";
					}
					break;
				}
			    case "13":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHJIKOKUTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHJIKOKUTEST";
						topic = "PUSHJIKOKU";
					}
					break;
				}
			    case "19":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHBABATEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHBABATEST";
						topic = "PUSHBABA";
					}
					break;
				}
			    case "20":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHWEATHERTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHWEATHERTEST";
						topic = "PUSHWEATHER";
					}
					break;
				}
			    case "50":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHUTOKUBETSUTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHUTOKUBETSUTEST";
						topic = "PUSHUTOKUBETSU";
					}
					break;
				}
			    case "51":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHMOKUDENTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHMOKUDENTEST";
						topic = "PUSHMOKUDEN";
					}
					break;
				}
			    case "52":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHYOKUDENTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHYOKUDENTEST";
						topic = "PUSHYOKUDEN";
					}
					break;
				}
			    case "53":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHUMAWEIGHTTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHUMAWEIGHTTEST";
						topic = "PUSHUMAWEIGHT";
					}
					break;
				}
			    case "54":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHHARAITEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHHARAITEST";
						topic = "PUSHHARAI";
					}
					break;
				}
			    case "90":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHTHISWEEKTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHTHISWEEKTEST";
						topic = "PUSHTHISWEEK";
					}
					break;
				}
			    case "91":{
					if (targetCondition.equals("1")) {
						topicTest = "PUSHNEWSTEST";
					}
					if (targetCondition.equals("0")) {
						topicTest = "PUSHNEWSTEST";
						topic = "PUSHNEWS";
					}
					break;
				}
			}
			String topicsString = "";
			if (targetCondition.equals("0")) {
				topicsString = "'" + topic + "' in topics || '" + topicTest+ "' in topics ";
			}
			if (targetCondition.equals("1")) {
				topicsString = "'" + topicTest + "' in topics";
			}
			
				Message msg = Message.builder()
                        .setCondition(topicsString)
                        .setNotification(notificationBuilder)
                        .putAllData(mapData)
                        .build();

                fcm.send(msg);
			

	    	if(!isAuto.equals("1")){
		        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		    	//Create record on table A05
		    	AppNotification appNotification = new AppNotification();
		        appNotification.setMessageCode(currentDateTime.format(formatterId));
		    	appNotification.setTitle(a08Record.getChgTitle());
		    	appNotification.setMessage(a08Record.getChgText());
		    	appNotification.setLinkUrl(linkUrl);
		    	appNotification.setExpireDay(a08Record.getHapyoTime());
		    	appNotification.setSndKbn(isAuto); //0: Manual 1: Automatic
		    	appNotification.setMkyMd(currentDateTime.format(dateFormatter));
		    	appNotification.setUpdTimestamp(currentDateTime);
				notificationRepository.save(appNotification);

				List<UserNotification> a04List = new ArrayList<>();
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
			}
	    	String senkbn = a08Record.getSendKbn();
	        if(isAuto.equals("1")
	        		&&(!senkbn.substring(0, 2).equals("61")
	        		&&!senkbn.substring(0, 2).equals("62")
	        		&&!senkbn.substring(0, 2).equals("63")
	        		&&!senkbn.substring(0, 2).equals("64"))) {
		        a08Record.setPushFlag("0");
		        a08ARepository.save(mapper.map(a08Record,A08A.class));
	        }
	        return true;
        }
		catch(Exception e) {
    		System.err.println("Exception: " + e.getMessage());
	        return false;
		}
    }

	@Override
	public Boolean checkTemplate() {
		int countTemplate = templateRepository.countAll();
		// check 100 template
		if(countTemplate <100){
			return true;
		}
		return false;
	}

	@Override
	public Asys02 addUrgentNotice(String title, String info, String hyojiStart, String hyojiEnd,
			Optional<Boolean> yukoFlg) {
		LocalDateTime dateTimeHyojiStart = LocalDateTime.parse(hyojiStart);
		LocalDateTime dateTimeHyojiEnd = LocalDateTime.parse(hyojiEnd);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		String formattedDateTime1 = dateTimeHyojiStart.format(formatter);
		String formattedDateTime2 = dateTimeHyojiEnd.format(formatter);
		Asys02 asys02 = new Asys02();
		asys02.setTitle(title);
		asys02.setInfo(info);
		if(yukoFlg.isPresent()) {
			asys02.setYukoFlg("ON");
		}else {
			asys02.setYukoFlg("OFF");
		}
		
		asys02.setHyojiStart(formattedDateTime1);
		asys02.setHyojiEnd(formattedDateTime2);
		return asys02;
	}
	@Override
	public void createUrgentNotice(@Valid Asys02 asys02Data) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime dateTimeHyojiStart = LocalDateTime.parse(asys02Data.getHyojiStart(),inputFormatter);
		LocalDateTime dateTimeHyojiEnd = LocalDateTime.parse(asys02Data.getHyojiEnd(),inputFormatter);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String formattedDateTime1 = dateTimeHyojiStart.format(formatter);
		String formattedDateTime2 = dateTimeHyojiEnd.format(formatter);
		Asys02 asys02 = asys02Repository.findById("1").get();
		if(asys02Data.getYukoFlg().equals("ON")) {
			asys02.setYukoFlg("1");
		}else {
			asys02.setYukoFlg("0");
		}
		asys02.setTitle(asys02Data.getTitle());
		asys02.setInfo(asys02Data.getInfo());
		asys02.setHyojiStart(formattedDateTime1);
		asys02.setHyojiEnd(formattedDateTime2);
		asys02.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
		asys02Repository.save(asys02);
	}
	
	@Override
	public Asys02 findById() {
		Asys02 asys = asys02Repository.findById("1").get();
		return asys;
	}
	@Override
	public Asys01 findByIdSystem(String keycode) {
		Asys01 asys = asys01Repository.findById(keycode).get();
		return asys;
	}
	@Override
	public void updateSystemConstant(String name, String value, String memo, String keycode) {
		Asys01 asys = asys01Repository.findById(keycode).get();
		asys.setSys01Memo(memo);
		asys.setSys01Name(name);
		asys.setSys01Value(value);
		asys.setUpdateTimestamp(Timestamp.valueOf(LocalDateTime.now()));
		asys01Repository.save(asys);
	}
	@Override
	public Asys01 confirmSystemConstant(String name, String value, String memo, String keycode) {
		Asys01 asys = asys01Repository.findById(keycode).get();
		asys.setSys01Memo(memo);
		asys.setSys01Name(name);
		asys.setSys01Value(value);
		return asys;
	}
	@Override
	public void deleteSystemConstant(String keycode) {
		asys01Repository.deleteById(keycode);
	}
	
	private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        int dataSize = (len + 1) / 2;

        byte[] data = new byte[dataSize];

        for (int i = 0; i < len; i += 2) {
            char firstNibble = s.charAt(i);
            char secondNibble = (i + 1 < len) ? s.charAt(i + 1) : '0';

            data[i / 2] = (byte) ((Character.digit(firstNibble, 16) << 4)
                    + Character.digit(secondNibble, 16));
        }

        return data;
    }
}
