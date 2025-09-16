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
package com.imt.login.component;
import com.imt.login.model.Asys01;
import com.imt.login.repository.Asys01Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.imt.login.service.CronJobService;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CronJob {
	@Autowired
	CronJobService cronJobService;

	@Autowired
	Asys01Repository asys01Repository;
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
	    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
	    threadPoolTaskScheduler.setPoolSize(5);
	    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
	    return threadPoolTaskScheduler;
	}

	//second, minute, hour, day of month, month, day(s) of week
	//This cron job will run every 30 seconds
    @Scheduled(cron = "*/30 * * * * *")
    public void sendNotificationJob() {
		System.out.println("Starting cron job sendNotification");
		try {
			//get host name
			InetAddress localMachine = InetAddress.getLocalHost();
			String hostname = localMachine.getHostName();
			System.out.println("Hostname: " + hostname);

			//get host name from db with key code = 0012
			Asys01 asys01 = asys01Repository.findAllByKeyCode();
			// check condition with host name
			if(asys01.getSys01Value().equals(hostname)){
				cronJobService.sendNotification();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
    // each 12 hour
	@Scheduled(cron = "0 0 */12 * * *")
  public void deleteAllFileCSV() throws IOException {
	  Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
      List<Path> csvFiles = Files.list(Paths.get(tempDir.toString()))
              .filter(path -> path.toString().toLowerCase().contains("listuser"))
              .collect(Collectors.toList());
      for (Path csvFile : csvFiles) {
          Files.deleteIfExists(csvFile);
      }
      System.out.println("Executing a task every 12 hours using cron.");
  }

    @Scheduled(cron = "0 */3 * * * *")
    public void sendNotificationA08BJob() {
		System.out.println("Starting cron job sendNotification A08B");
		try {
			//get host name
			InetAddress localMachine = InetAddress.getLocalHost();
			String hostname = localMachine.getHostName();
			System.out.println("Hostname: " + hostname);
			//get host name from db with key code = 0012
			Asys01 asys01 = asys01Repository.findAllByKeyCode();
			// check condition with host name
			if(asys01.getSys01Value().equals(hostname)){
				cronJobService.sendNotificationA08B();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
