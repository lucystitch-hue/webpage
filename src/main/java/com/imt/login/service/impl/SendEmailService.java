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

import com.imt.login.repository.AppUserRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SendEmailService {

    @Autowired
    private MailSender sender;

    @Value("${env.email-to}")
    private String emailTo;

    @Value("${env.email-from}")
    private String emailFrom;

    public Map<String, Object> sendEmail(String emailUser, String category, String device, String system, String steelSueName, String africaVersion, String content) {
    	Map<String, Object> rs = new HashMap<>();
        try {
	    	if (StringUtils.isBlank(emailUser) || StringUtils.isBlank(category) || StringUtils.isBlank(device) || StringUtils.isBlank(system) || StringUtils.isBlank(content)) {
	    		rs.put("success", false);
	    		rs.put("message", "Inquiry has been <br> submitted!");
	            return rs;
	        }
	    	String emailSubject = "Horse Racing App Inquiry Received (" + category + ")";
	    	String emailContent = "-------------------------------------\n" +
	        		"※ This is an automated email sent to customers who have made inquiries for confirmation purposes.\n" +
	        		"※ Please note that we cannot respond to replies to this email. \n" +
	        		"-------------------------------------\n\n" +
	        		"Thank you for your inquiry about the Horse Racing App.\n" +
	                "We would like to inform you that we have received your inquiry.\n" +
	                "＜Inquiry Details＞\n\n" +
	                "Email Address: " + emailUser + "\n" +
	                "Inquiry Category: " + category + "\n" +
	                "Device Used: " + device + "\n" +
	                "Operating System: " + system + "\n" +
	                "Device Name (Model/Type): " + steelSueName + "\n" +
	                "App Version: " + africaVersion + "\n" +
	                "Inquiry Content: " + content + "\n\n" +
	                "We will review your inquiry based on the information provided.\n" +
					"We strive for prompt responses, but it may take several days for confirmation.\n" +
					"Please note that we may not be able to reply to opinions and requests.\n" +
					"We hope you will continue to use the Horse Racing App.\n\n" +
	                "Horse Racing Team";
	    	
	    	// Send email to Horse Racing staff
	        String[] configEmails = emailTo.split(",");
	        int emailToCount = configEmails.length;
	        for (int i = 0; i < emailToCount; i++) {
	        	if(!StringUtils.isBlank(configEmails[i])) {
		        	String mailTo = configEmails[i];
			        sendEmail(mailTo, emailSubject, emailContent);
	        	}
	        }
	        
	        // Send email to user
	        sendEmail(emailUser, emailSubject, emailContent);
	       
	        rs.put("success", true);
    		rs.put("message", "Inquiry has been sent successfully!");
            return rs;
        }
		catch(Exception e) {
    		System.err.println("Exception: " + e.getMessage());
            rs.put("success", false);
    		rs.put("message", e.getMessage());
            return rs;
		}
    }

    public Boolean sendEmail(String emailTo, String emailSubject, String emailContent) {
    	try {
        	SimpleMailMessage msg = new SimpleMailMessage();
    	        msg.setFrom(emailFrom);
    	        msg.setTo(emailTo);
    	        msg.setSubject(emailSubject);
    	        msg.setText(emailContent); // Set message content
    	        System.out.printf(">>> Sent inquiry email to email: " + emailTo);
    	        this.sender.send(msg);
        		return true;
    	}catch(Exception e) {
    		System.err.println("Exception: " + e.getMessage());
    		return false;
    	}
    }
}
