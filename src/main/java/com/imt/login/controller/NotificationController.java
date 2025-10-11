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
package com.imt.login.controller;

import com.imt.login.dto.A09ListPagingDTO;
import com.imt.login.dto.AppUserCSVListPagingDTO;
import com.imt.login.dto.Asys01ListPagingDTO;
import com.imt.login.dto.NotificationForm;
import com.imt.login.model.A08A;
import com.imt.login.model.AppNotification;
import com.imt.login.model.AppUser;
import com.imt.login.model.Asys02;
import com.imt.login.model.Template;
import com.imt.login.service.NotificationService;
import com.imt.login.service.impl.A09Service;
import com.imt.login.service.impl.Asys01Service;
import com.imt.login.service.impl.SendEmailService;
import com.imt.login.service.impl.UserDetailsServices;
import com.imt.login.utils.CSV;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    UserDetailsServices userDetailsServices;

    @Autowired
    NotificationService notificationService;

    @Autowired
    JobScheduler jobScheduler;

    @Autowired
    SendEmailService sendEmailService;
    
    @Autowired
	Asys01Service asys01Service;
    
    @Autowired
	A09Service a09Service;

    @Value("${env.email-error-to}")
    private String emailErrorTo;

    @RequestMapping("/login")
    public String loginAppNotificationManagement(Model model) {
        model.addAttribute("header", "Horse Racing App Management");
        model.addAttribute("url", "login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            userDetailsServices.clearData();
            return "login-app-notification";
        }
        return "redirect:notification-management";
    }

    @GetMapping("/notification-management")
    public String notificationManagement(Model model) {
        model.addAttribute("list", notificationService.listAll());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("minTime", formattedDateTime);
        model.addAttribute("maxTime", "2100-01-01T00:00");
        model.addAttribute("header", "Push Notification Management");
        return "push-notification-management-page";
    }

    @GetMapping("/notification-management/{templateId}")
    public String notificationLoadTemplate(Model model, @PathVariable("templateId") String templateId) {
        model.addAttribute("list", notificationService.listAll());
        model.addAttribute("template", notificationService.findByIdTemplate(templateId));
        model.addAttribute("header", "Push Notification Management");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("minTime", formattedDateTime);
        model.addAttribute("maxTime", "2100-01-01T00:00");
        return "push-notification-management-page";
    }
    @GetMapping("/notification-management/send-notification")
    public String redirecPage(Model model) {
        model.addAttribute("list", notificationService.listAll());
        model.addAttribute("header", "Push Notification Management");
        return "push-notification-management-page";
    }
    @PostMapping("/notification-management/send-notification")
    public String sendNotification(Model model, @ModelAttribute NotificationForm notificationForm) throws IOException {
        model.addAttribute("newNotification", notificationService.createNotification(notificationForm.getChgTitle(),notificationForm.getChgText(),notificationForm.getLinkUrl(),notificationForm.getHapyoTime()));
        CSV.fileCsvListUser(notificationForm.getMyFile().get());
        switch (notificationForm.getCatalog()) {
            case "90":
                model.addAttribute("catalogName", "This Week's Highlights");
                model.addAttribute("catalog", "90");
                break;
            case "91":
                model.addAttribute("catalogName", "Great Deal Information");
                model.addAttribute("catalog", "91");
                break;
            case "62":
                model.addAttribute("catalogName", "All Users");
                model.addAttribute("catalog", "62");
                break;
        }
        switch (notificationForm.getCondition()) {
            case "0":
                model.addAttribute("conditionName", "ALL");
                model.addAttribute("condition", "0");
                break;
            case "1":
                model.addAttribute("conditionName", "Test User");
                model.addAttribute("condition", "1");
                break;
            case "2":
                model.addAttribute("conditionName", "CSV Import");
                model.addAttribute("condition", "2");
                model.addAttribute("fileCSV", notificationForm.getMyFile().get().getOriginalFilename());
//                model.addAttribute("contentFile", new String(notificationForm.getMyFile().get().getBytes()));
                break;
        }

        model.addAttribute("header", "Push Notification Management");
        return "confirmation-screen-page";
    }

    @GetMapping("/notification-management/completed")
    public String redirectPageHome(Model model) {
        model.addAttribute("list", notificationService.listAll());
        model.addAttribute("header", "Push Notification Management");
        return "push-notification-management-page";
    }

    @PostMapping("/notification-management/completed")
    public String completedScreen(Model model, @ModelAttribute NotificationForm notificationForm) {
        try {
            jobScheduler.enqueue(() -> notificationService.sendNotification(
                    notificationForm.getChgTitle(),
                    notificationForm.getChgText(),
                    notificationForm.getLinkUrl(),
                    notificationForm.getHapyoTime(),
                    notificationForm.getCatalog(),
                    notificationForm.getCondition(),
                    notificationForm.getFileName()));
            model.addAttribute("result", "Transmission completed.");
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            // send email when jobScheduler was error
            sendEmailService.sendEmail(emailErrorTo, "Manual Push notification Exception", e.getMessage());
            model.addAttribute("result", "Transmission failed");
        }
        
        model.addAttribute("header", "Push Notification Management");
        return "completed-screen-page";
    }

    @GetMapping("/notification-template")
    public String notificationTemplate(Model model,@RequestParam("check") Optional<Boolean> check) {
        model.addAttribute("list", notificationService.listAll());
        model.addAttribute("count", notificationService.listAll().size());
        if(!check.orElse(true)){
            model.addAttribute("message", "You cannot register more than 100 templates.");
        }
        model.addAttribute("header", "Notification Template");
        return "notification-template-page";
    }

    @GetMapping("/notification-template/new-template")
    public String newTemplate(Model model) {
        model.addAttribute("header", "Notification Template");
        Boolean checkTemplates = notificationService.checkTemplate();
        if(!checkTemplates){
            return "redirect:/notification/notification-template?check=false";
        }
        return "new-template-page";
    }

    @PostMapping("/notification-template/confirm-new-template")
    public String confirmNewTemplate(Model model,
                                     @RequestParam("titleFormat") String title,
                                     @RequestParam("teikeiFormat") String message,
                                     @RequestParam("linkUrl") String linkURL) {
        model.addAttribute("newTemplate", notificationService.addTemplate(title, message, linkURL));
        model.addAttribute("header", "Notification Template");
        return "confirm-new-template-page";
    }

    @GetMapping("/notification-template/cancel/{templateId}")
    public String cancelRequest(Model model, @PathVariable(name = "templateId") String templateId) {
        notificationService.delete(templateId);
        return "redirect:/notification/notification-template";
    }

    @PostMapping("/notification-template/create-template")
    public String createTemplate(Model model,@Valid Template template) {
        notificationService.createTemplate(template);
        model.addAttribute("header", "Notification Template");
        return "redirect:/notification/notification-template";
    }

    @PostMapping("/notification-template/edit-template/{templateId}")
    public String confirmNewTemplate(Model model, @Valid Template template, @PathVariable(name = "templateId") String templateId) {
        notificationService.edit(templateId, template);
        model.addAttribute("header", "Notification Template");
        return "redirect:/notification/notification-template";
    }

    @GetMapping("/notification-template/detail-template/{templateId}")
    public String findDetail(Model model, @PathVariable(name = "templateId") String templateId) {
        model.addAttribute("template", notificationService.findByIdTemplate(templateId));
        model.addAttribute("header", "Notification Template");
        return "edit-template-page";
    }
    
    @GetMapping("/urgent-notice")
    public String urgentNotice(Model model) {
        model.addAttribute("header", "Emergency Notice");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        Asys02 asys = notificationService.findById();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        // Parse the input string into LocalDateTime
        LocalDateTime dateTimeStart = LocalDateTime.parse(asys.getHyojiStart(), inputFormatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(asys.getHyojiEnd(), inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTimeEnd = dateTimeEnd.format(outputFormatter);
        String formattedDateTimeStart = dateTimeStart.format(outputFormatter);
        model.addAttribute("hyojiStart", formattedDateTimeStart);
        model.addAttribute("hyojiEnd", formattedDateTimeEnd);
        model.addAttribute("urgentNotice", notificationService.findById());
		if (notificationService.findById().getYukoFlg().equals("1")) {
			model.addAttribute("yukoFlg", true);
		}else {
			model.addAttribute("yukoFlg", false);
		}
		
        model.addAttribute("minTime", formattedDateTime);
        model.addAttribute("maxTime", "2100-01-01T00:00");
        return "urgent-notice-page";
    }
    
    @PostMapping("/urgent-notice/confirm-new-urgent-notice")
    public String confirmUrgentNotice(Model model,
                                     @RequestParam("title") String title,
                                     @RequestParam("info") String info,
                                     @RequestParam("hyojiStart") String hyojiStart,
                                     @RequestParam("hyojiEnd") String hyojiEnd,
                                     @RequestParam(name = "yukoFlg") Optional<Boolean> yukoFlg){
        model.addAttribute("newUrgentNotice", notificationService.addUrgentNotice(title, info, hyojiStart, hyojiEnd, yukoFlg));
        model.addAttribute("header", "Emergency Notice");
        return "confirm-new-urgent-notice-page";
    }
    
    @PostMapping("/urgent-notice/create-urgent-notice")
    public String createUrgentNotice(Model model,@Valid Asys02 asys02) {
        notificationService.createUrgentNotice(asys02);
        model.addAttribute("header", "Emergency Notice");
        return "redirect:/notification/urgent-notice";
    }
    
    @GetMapping("/featured-races-overseas")
    public String featuredRacesOverseas(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("header", "Featured Overseas Races");
        int currentPage = page.orElse(1);
        A09ListPagingDTO coursePage = a09Service.getListA09(PageRequest.of(currentPage-1, 12));
        
        	model.addAttribute("currentPage", coursePage.getCurrentPage());
            model.addAttribute("countA09", coursePage.getTotalResults());
            model.addAttribute("listA09", coursePage.getListA09());
            model.addAttribute("totalPages", coursePage.getTotalPage());
            int totalPages = coursePage.getTotalPage();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        return "featured-races-overseas-page";
    }
    
    @GetMapping("/system-constants")
    public String systemConstants(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("header", "System Constants");
        int currentPage = page.orElse(1);
        Asys01ListPagingDTO coursePage = asys01Service.getListAsys01(PageRequest.of(currentPage - 1, 12));
        
        	model.addAttribute("currentPage", coursePage.getCurrentPage());
            model.addAttribute("countAsys01", coursePage.getTotalResults());
            model.addAttribute("listAsys01", coursePage.getListAsys01s());
            model.addAttribute("totalPages", coursePage.getTotalPage());
            int totalPages = coursePage.getTotalPage();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        
        return "system-constants-page";
    }
    
    @GetMapping("/system-constants/edit/{keycode}")
    public String detailSystemConstant(Model model,@PathVariable(name = "keycode") String keycode) {
    	model.addAttribute("system", notificationService.findByIdSystem(keycode));
        model.addAttribute("header", "System Constants");
    	return "edit-system-constants-page";
    }
    
    @PostMapping("/system-constants/edit/{id}")
    public String updateSystemConstant(Model model,
    								@PathVariable(name = "id") String keycode,
                                     @RequestParam("sys01Name") String name,
                                     @RequestParam("sys01Value") String value,
                                     @RequestParam("sys01Memo") String memo){
        notificationService.updateSystemConstant(name, value, memo, keycode);
        
        return "redirect:/notification/system-constants";
    }
    
    @GetMapping("/featured-races-overseas/newRegistration")
    public String newRegistrationFeatured(Model model) {
        model.addAttribute("header", "Featured Overseas Races");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("minTime", formattedDateTime);
        model.addAttribute("maxTime", "2100-01-01T00:00");
    	return "new-registration-featured";
    }
    
    @PostMapping("/featured-races-overseas/confirm")
    public String confirmFeatured(Model model,
    								@RequestParam("kaisaiYmd") String kaisaiYmd,
                                     @RequestParam("raceName") String raceName,
                                     @RequestParam("grd") String grd,
                                     @RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate){

		model.addAttribute("a09", a09Service.confirma09(kaisaiYmd, raceName, grd, startDate, endDate));
		model.addAttribute("header", "Featured Overseas Races");
		LocalDateTime startDateTime = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime endDateTime = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		model.addAttribute("startDate", startDateTime.format(outputFormatter));
		model.addAttribute("endDate", endDateTime.format(outputFormatter));
		return "confirm-registration-featured";
    }
    
    @PostMapping("/featured-races-overseas/create")
    public String createFeatured(Model model,
    								@RequestParam("kaisaiYmd") String kaisaiYmd,
                                     @RequestParam("raceName") String raceName,
                                     @RequestParam("grd") String grd,
                                     @RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate){
        a09Service.createA09(kaisaiYmd, raceName, grd, startDate, endDate);
        return "redirect:/notification/featured-races-overseas";
    }
    
    @GetMapping("/featured-races-overseas/{kaisaiYmd}/{raceName}")
    public String getFeatured(Model model,
    								@PathVariable(name = "kaisaiYmd") String kaisaiYmd,
    								@PathVariable(name = "raceName") String raceName){
        model.addAttribute("a09",a09Service.findById(kaisaiYmd,raceName));
        model.addAttribute("header", "Featured Overseas Races");
        return "edit-registration-featured";
    }
    
    @GetMapping("/featured-races-overseas/delete/{kaisaiYmd}/{raceName}")
    public String deleteFeatured(Model model,
    								@PathVariable(name = "kaisaiYmd") String kaisaiYmd,
    								@PathVariable(name = "raceName") String raceName){
        a09Service.delete(kaisaiYmd,raceName);
        return "redirect:/notification/featured-races-overseas";
    }
    
    @PostMapping("/featured-races-overseas/edit")
    public String editFeatured(Model model,
    		@RequestParam("kaisaiYmdOriginal") String kaisaiYmdOriginal,
            @RequestParam("raceNameOriginal") String raceNameOriginal,
    								@RequestParam("kaisaiYmd") String kaisaiYmd,
                                     @RequestParam("raceName") String raceName,
                                     @RequestParam("grd") String grd,
                                     @RequestParam("startDate") String startDate,
                                     @RequestParam("endDate") String endDate){
        a09Service.editA09(kaisaiYmd, raceName, grd, startDate, endDate,kaisaiYmdOriginal,raceNameOriginal);
        return "redirect:/notification/featured-races-overseas";
    }
}
