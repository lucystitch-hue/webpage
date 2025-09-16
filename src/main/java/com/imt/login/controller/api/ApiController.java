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
package com.imt.login.controller.api;

import com.imt.login.service.CronJobService;
import com.imt.login.service.impl.A09Service;
import com.imt.login.service.impl.UserDetailsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
	CronJobService cronJobService;
    
    @Autowired
    UserDetailsServices userDetailsServices;
    
    @Autowired
	A09Service a09Service;

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
    
    @GetMapping("/checkA09/{key}/{name}")
    public boolean checkPrimaryA09(@PathVariable(name = "key") String key, @PathVariable(name = "name") String name) {
    	return a09Service.checkKey(key, name);
    }
    
    @GetMapping("/checkA09/{key}/{name}/{keyOriginal}/{nameOriginal}")
    public boolean checkPrimaryA09Edit(@PathVariable(name = "key") String key,
    		@PathVariable(name = "name") String name,
    		@PathVariable(name = "keyOriginal") String keyOriginal,
    		@PathVariable(name = "nameOriginal") String nameOriginal) {
    	return a09Service.checkKeyEdit(key, name,keyOriginal,nameOriginal);
    }
    
    @GetMapping("/checkUserAdmin/{username}")
    public boolean checkUsernameAdmin(@PathVariable(name = "username") String username) {
    	return userDetailsServices.checkUserAdmin(username);
    }
    
    @GetMapping("/checkUserNotice/{username}")
    public boolean checkUsernameNotice(@PathVariable(name = "username") String username) {
    	return userDetailsServices.checkUserNotice(username);
    }

    @GetMapping("/run-cron-job/{jobName}")
    public String runCronJob(@PathVariable(name = "jobName") String jobName) {
    	System.out.println("------------");
    	System.out.println("Calling manually cron job: - " + jobName);

    	switch (jobName) {
    	 case "sendNotification":
    		 System.out.println("----111----");
    		 cronJobService.sendNotification();
    		 System.out.println("----222----");
             break;
         default:
        	 return "This cron job was not found";
    	}

    	return "This cron job was executed successfully";
    }
}
