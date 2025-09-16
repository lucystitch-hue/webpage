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
 *     Modified date:  17/11/2023
 *     Corresponding : slide 15-20
 *     Description of change:
 *      - remove condition not use
 */

package com.imt.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.gson.Gson;
import com.imt.login.dto.A09ListPagingDTO;
import com.imt.login.dto.AppUserCSVDTO;
import com.imt.login.dto.AppUserCSVListPagingDTO;
import com.imt.login.dto.ListTemporaryUserDTO;
import com.imt.login.dto.LogListPagingDTO;
import com.imt.login.dto.TemporaryUserDTO;
import com.imt.login.model.AppUser;
import com.imt.login.service.impl.DeleteUserTemPoraryRunnable;
import com.imt.login.service.impl.OCIService;
import com.imt.login.service.impl.UserDetailsServices;
import com.imt.login.utils.CSV;
import com.mysql.cj.util.StringUtils;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/admin")
@CrossOrigin
public class AppUserController {
    @Autowired
    UserDetailsServices userDetailsServices;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    JobScheduler jobScheduler;

    @Autowired
    OCIService ociService;
     @Autowired
     DeleteUserTemPoraryRunnable deleteUserTemPoraryRunnable;
    /**
     * This is a Java code snippet that defines a method with the `@GetMapping` annotation, which maps an
     * HTTP GET request to the specified URL path "/list-user".
     * The method returns a String "index" and adds an attribute "listUser" to the Model object.
     * The value of the "listUser" attribute is obtained by calling the `getListUser()` method
     * of the `userDetailsServices` object.
     * This method is typically used in a Spring MVC web application to display a list of users on the index page.
     * request: http://localhost:8080/admin/list-user
     */
    @GetMapping("/user")
    public String home(Model model, @RequestParam("page") Optional<Integer> page,
                       @RequestParam("keyWord") Optional<String> keyWord
    ) {
        model.addAttribute("header", "User Information Management");
        int currentPage = page.orElse(1);
        AppUserCSVListPagingDTO coursePage = userDetailsServices.getListUser(PageRequest.of(currentPage - 1, 20)
                , keyWord.orElse(null));
        if(keyWord.equals(Optional.empty())){
            model.addAttribute("countUser", coursePage.getTotalResults());
            return "home-user-page";
        }
        if( keyWord.get().equals("")){
            model.addAttribute("countUser", 0);
            return "home-user-page";
        }
        else {
        	model.addAttribute("currentPage", coursePage.getCurrentPage());
            model.addAttribute("countUser", coursePage.getTotalResults());
            model.addAttribute("listUser", coursePage.getLisAppUserCSVDTOs());
            model.addAttribute("totalPages", coursePage.getTotalPage());
            int totalPages = coursePage.getTotalPage();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("keyWord", keyWord.orElse(null));
            }

        }

        return "home-user-page";
    }

    @GetMapping("/csv-download")
    public String csvDownload(Model model) {
        model.addAttribute("header", "CSV Download");
        return "down-csv-page";
    }


    /**
     * This is a Java method annotated with `@GetMapping` which maps HTTP GET requests to the "/login" endpoint.
     * request: http://localhost:8080/admin/login
     */
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("header", "Horse Racing App User Management");
        model.addAttribute("url", "login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            userDetailsServices.clearData();
            return "login-app-user";
        }
        return "redirect:/admin/user";
    }

    /**
     * This is a Java method annotated with `@PostMapping` which maps HTTP POST requests to the "/csv" endpoint.
     * It takes in a `MultipartFile` object named "multipartFile" as a request parameter using
     * the `@RequestPart` annotation.
     * The method then calls a service method `csvReader` passing in the `multipartFile` object and
     * gets back a list of `AppUserDTO` objects.
     * It then prints the `userId` and `userName` of each object in the list using a lambda expression and
     * returns a redirect to the "/admin/user" endpoint.
     * request: http://localhost:8080/admin/csv
     */
//    @PostMapping("/csv")
//    public String csvReader(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
//        List<AppUserCSVDTO> detailUser = userDetailsServices.csvReader(multipartFile);
//
//        detailUser.forEach(model ->
//                System.out.println(model.getUserId() + "\t" + model.getSei())
//        );
//        return "redirect:/admin/user";
//    }

    @GetMapping(value = "/csv-download-2")
    public void downloadCsv(HttpServletResponse response) throws IOException {
    	File csvDown = userDetailsServices.downloadCSV();
        Resource resource = new FileSystemResource(csvDown);
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ListUser.csv");

        InputStream inputStream = resource.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        try {
        	
        
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        }catch (Exception e) {
        	inputStream.close();
            outputStream.flush();
		}
        inputStream.close();
        outputStream.flush();
        
    }

    @PostMapping("/edit-user/{uId}")
    public String editUser(@PathVariable(name = "uId") String userId, AppUser user,
                           BindingResult result, Model model,
                           @RequestParam(name = "o0") Optional<Boolean> option0,
                           @RequestParam(name = "o1") Optional<Boolean> option1,
                           @RequestParam(name = "o2") Optional<Boolean> option2,
                           @RequestParam(name = "o3") Optional<Boolean> option3,
                           @RequestParam(name = "o4") Optional<Boolean> option4,
                           @RequestParam(name = "o5") Optional<Boolean> option5,
                           @RequestParam(name = "o6") Optional<Boolean> option6,
                           @RequestParam(name = "o7") Optional<Boolean> option7,
                           @RequestParam(name = "o8") Optional<Boolean> option8,
                           @RequestParam(name = "o9") Optional<Boolean> option9,
                           @RequestParam(name = "o10") Optional<Boolean> option10,
                           @RequestParam(name = "o11") Optional<Boolean> option11,
                           @RequestParam(name = "o12") Optional<Boolean> option12,
                           @RequestParam(name = "o13") Optional<Boolean> option13,
                           @RequestParam(name = "o14") Optional<Boolean> option14,
                           @RequestParam(name = "o15") Optional<Boolean> option15,
                           @RequestParam(name = "o16") Optional<Boolean> option16,
                           @RequestParam(name = "o17") Optional<Boolean> option17,
                           @RequestParam(name = "o18") Optional<Boolean> option18,
                           @RequestParam(name = "o19") Optional<Boolean> option19,
                           @RequestParam(name = "o20") Optional<Boolean> option20,
                           @RequestParam(name = "o21") Optional<Boolean> option21,
                           @RequestParam(name = "o22") Optional<Boolean> option22,
                           @RequestParam(name = "o23") Optional<Boolean> option23,
                           @RequestParam(name = "o24") Optional<Boolean> option24,
                           @RequestParam(name = "o25") Optional<Boolean> option25,
                           @RequestParam(name = "o26") Optional<Boolean> option26,
                           @RequestParam(name = "o27") Optional<Boolean> option27) throws FirebaseMessagingException, NotFoundException {
        if (result.hasErrors()) {
            return "redirect:/admin/user/" + userId;
        }
//        @RequestParam(name = "o28") Optional<Boolean> option28,
//        @RequestParam(name = "o29") Optional<Boolean> option29,
//        @RequestParam(name = "o30") Optional<Boolean> option30,
//        @RequestParam(name = "o31") Optional<Boolean> option31,
//        @RequestParam(name = "o32") Optional<Boolean> option32,
//        @RequestParam(name = "o33") Optional<Boolean> option33,
//        @RequestParam(name = "o34") Optional<Boolean> option34,
//        @RequestParam(name = "o35") Optional<Boolean> option35,
//        @RequestParam(name = "o36") Optional<Boolean> option36,
//        @RequestParam(name = "o37") Optional<Boolean> option37,
//        @RequestParam(name = "o38") Optional<Boolean> option38,
//        @RequestParam(name = "o39") Optional<Boolean> option39,
//        @RequestParam(name = "o40") Optional<Boolean> option40
        List<Boolean> uService = new ArrayList<>();
        uService.add(option0.orElse(false));
        uService.add(option1.orElse(false));
        uService.add(option2.orElse(false));
        uService.add(option3.orElse(false));
        uService.add(option4.orElse(false));
        List<Boolean> uReason = new ArrayList<>();
        uReason.add(option5.orElse(false));
        uReason.add(option6.orElse(false));
        uReason.add(option7.orElse(false));
        uReason.add(option8.orElse(false));
        uReason.add(option9.orElse(false));
        uReason.add(option10.orElse(false));
        uReason.add(option11.orElse(false));
        uReason.add(option12.orElse(false));
        uReason.add(option13.orElse(false));
        uReason.add(option14.orElse(false));
        List<Boolean> uDayOff = new ArrayList<>();
        uDayOff.add(option15.orElse(false));
        uDayOff.add(option16.orElse(false));
        uDayOff.add(option17.orElse(false));
        uDayOff.add(option18.orElse(false));
        uDayOff.add(option19.orElse(false));
        uDayOff.add(option20.orElse(false));
        uDayOff.add(option21.orElse(false));
        uDayOff.add(option22.orElse(false));
        uDayOff.add(option23.orElse(false));
        uDayOff.add(option24.orElse(false));
        uDayOff.add(option25.orElse(false));
        uDayOff.add(option26.orElse(false));
        uDayOff.add(option27.orElse(false));

        AppUserCSVDTO appUserCSVDTO = userDetailsServices.editUser(userId, user, uService, uReason, uDayOff);
        if(appUserCSVDTO == null) {
        	return "redirect:/admin/user/"+userId;
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/drop-user/{userId}")
    public String dropUser(@PathVariable(name = "userId") String userId) {
        userDetailsServices.dropUser(userId);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/{userId}")
    public String findUser(@PathVariable(name = "userId") String userId, Model model) throws ChangeSetPersister.NotFoundException {
        AppUser user = userDetailsServices.findUser(userId);
        model.addAttribute("header", "User Information Management/Edit");
        user.setPostcode(user.getPostcode().trim());
        model.addAttribute("detailUser", user);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        model.addAttribute("lastUpdate", user.getUpdateTimestamp().format(inputFormatter));
        Gson gson = new Gson();
        if (user.getUserService() != null) {
//            Map<String, Object> map = gson.fromJson(user.getUserService(), Map.class);
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                model.addAttribute(entry.getKey(), user);
//            }
            for (int i = 0; i < user.getUserService().length(); i++) {
                char c = user.getUserService().charAt(i);
                int number = Character.getNumericValue(c);
                model.addAttribute("optionS" + i, number);
            }
        }
        if (user.getUserReason() != null) {
//            Map<String, Object> map2 = gson.fromJson(user.getUserReason(), Map.class);
//            for (Map.Entry<String, Object> entry : map2.entrySet()) {
//                model.addAttribute(entry.getKey(), user);
//            }
            for (int i = 0; i < user.getUserReason().length(); i++) {
                char c = user.getUserReason().charAt(i);
                int number = Character.getNumericValue(c);
                model.addAttribute("optionR" + i, number);
            }
        }

        if (user.getUserDayOff() != null) {
//            Map<String, Object> map3 = gson.fromJson(user.getUserDayOff(), Map.class);
//            for (Map.Entry<String, Object> entry : map3.entrySet()) {
//                model.addAttribute(entry.getKey(), user);
//            }
            for (int i = 0; i < user.getUserDayOff().length(); i++) {
                char c = user.getUserDayOff().charAt(i);
                int number = Character.getNumericValue(c);
                model.addAttribute("optionD" + i, number);
            }
        }
        
        if (user.getPushFavTokubetsu() != null && user.getPushFavTokubetsu().equals("1")) {
            model.addAttribute("pushFavTokubetsu", true);
        } else {
            model.addAttribute("pushFavTokubetsu", false);
        }
        if (user.getPushFavYokuden() != null && user.getPushFavYokuden().equals("1")) {
            model.addAttribute("pushFavYokuden", true);
        } else {
            model.addAttribute("pushFavYokuden", false);
        }
        if (user.getPushFavHarai() != null && user.getPushFavHarai().equals("1")) {
            model.addAttribute("pushFavHarai", true);
        } else {
            model.addAttribute("pushFavHarai", false);
        }
        if (user.getPushFavJoc() != null && user.getPushFavJoc().equals("1")) {
            model.addAttribute("pushFavJoc", true);
        } else {
            model.addAttribute("pushFavJoc", false);
        }        
        if (user.getPushTorikeshi() != null && user.getPushTorikeshi().equals("1")) {
            model.addAttribute("pushTorikeshi", true);
        } else {
            model.addAttribute("pushTorikeshi", false);
        }
        if (user.getPushJogai() != null && user.getPushJogai().equals("1")) {
            model.addAttribute("pushJogai", true);
        } else {
            model.addAttribute("pushJogai", false);
        }
        if (user.getPushKisyu() != null && user.getPushKisyu().equals("1")) {
            model.addAttribute("pushKisyu", true);
        } else {
            model.addAttribute("pushKisyu", false);
        }
        if (user.getPushJikoku() != null && user.getPushJikoku().equals("1")) {
            model.addAttribute("pushJikoku", true);
        } else {
            model.addAttribute("pushJikoku", false);
        }
        if (user.getPushBaba() != null && user.getPushBaba().equals("1")) {
            model.addAttribute("pushBaba", true);
        } else {
            model.addAttribute("pushBaba", false);
        }
        if (user.getPushWeather() != null && user.getPushWeather().equals("1")) {
            model.addAttribute("pushWeather", true);
        } else {
            model.addAttribute("pushWeather", false);
        }
        if (user.getPushUmaWeight() != null && user.getPushUmaWeight().equals("1")) {
            model.addAttribute("pushUmaWeight", true);
        } else {
            model.addAttribute("pushUmaWeight", false);
        }
        if (user.getPushHarai() != null && user.getPushHarai().equals("1")) {
            model.addAttribute("pushHarai", true);
        } else {
            model.addAttribute("pushHarai", false);
        }
        if (user.getPushUtokubetsu() != null && user.getPushUtokubetsu().equals("1")) {
            model.addAttribute("pushUtokubetsu", true);
        } else {
            model.addAttribute("pushUtokubetsu", false);
        }
        if (user.getPushMokuden() != null && user.getPushMokuden().equals("1")) {
            model.addAttribute("pushMokuden", true);
        } else {
            model.addAttribute("pushMokuden", false);
        }
        if (user.getPushYokuden() != null && user.getPushYokuden().equals("1")) {
            model.addAttribute("pushYokuden", true);
        } else {
            model.addAttribute("pushYokuden", false);
        }
        if (user.getPushThisWeek() != null && user.getPushThisWeek().trim().equals("1")) {
            model.addAttribute("pushThisWeek", true);
        } else {
            model.addAttribute("pushThisWeek", false);
        }
        if (user.getPushNews() != null && user.getPushNews().equals("1")) {
            model.addAttribute("pushNews", true);
        } else {
            model.addAttribute("pushNews", false);
        }
        if (user.getYsnFlag1() != null && user.getYsnFlag1().equals("1")) {
            model.addAttribute("ysnFlag1", "on");
        }
        if (user.getYsnFlag2() != null && user.getYsnFlag2().equals("1")) {
            model.addAttribute("ysnFlag2", "on");
        }
        return "edit-user-page";
    }

    @RequestMapping(value = "/temporaryUser", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String temporaryUser(Model model, @RequestParam("page") Optional<Integer> page,
                       @RequestParam("dateFrom") Optional<String> dateFrom,
                                @RequestParam("dateTo") Optional<String> dateTo
    ) {
        model.addAttribute("header", "Temporary User Registration Deletion");

        if(dateFrom.equals(Optional.empty()) && dateTo.equals(Optional.empty())){
            model.addAttribute("countUser", 0);
            return "temporary-user-page";
        }

        int currentPage = page.orElse(1);
        model.addAttribute("dateFrom", dateFrom.get());
        model.addAttribute("dateTo", dateTo.get());
        ListTemporaryUserDTO coursePage = ociService.getListTemporaryUser(dateFrom.get(),dateTo.get(),PageRequest.of(currentPage - 1, 20));
        if( dateFrom.get().equals("")&& dateTo.get().equals("")){
            model.addAttribute("countUser", 0);
            return "temporary-user-page";
        }
        else {
            model.addAttribute("countUser", Integer.valueOf(coursePage.getTotalResults()) );
            model.addAttribute("listUser", coursePage.getTemporaryUserDTO());

            model.addAttribute("currentPage", coursePage.getCurrentPage());
            int totalPages = coursePage.getTotalPage();
            model.addAttribute("totalPages", totalPages);
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
//                model.addAttribute("keyWord", keyWord.orElse(null));
            }

        }

        return "temporary-user-page";
    }

    
    @RequestMapping(value = "/temporaryUser/{userId}/{email}/{created}", method = RequestMethod.GET)
    
    public String dropUser(Model model, @PathVariable(name = "userId") String userId,
                                      @PathVariable(name = "email") String email,
                                      @PathVariable(name = "created") String created) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "ja");
        deleteUserTemPoraryRunnable.run(userId,created,email);
        model.addAttribute("header", "Temporary User Registration Deletion");
        model.addAttribute("key", userId);
        List<TemporaryUserDTO> temporaryUserDTOList = ociService.dataTemporaryUser.get(userId);
        if(temporaryUserDTOList == null){
            return "redirect:/admin/temporaryUser";
        }
        int countListAll = ociService.dataTemporaryUser.get(userId).size();
        Long countSuccess = ociService.dataTemporaryUser.get(userId)
                .stream().filter(i->i.isSuccessDelete()).count();
        Long countNotSuccess = ociService.dataTemporaryUser.get(userId)
                .stream().filter(i->!i.isSuccessDelete()).count();
        model.addAttribute("countNotSuccess", countNotSuccess);
        model.addAttribute("countSuccess", countSuccess);
        model.addAttribute("countAll", countListAll);
        return "temporary-user-success-page";
    }
    
    @RequestMapping(value = "/deleteAllUser/{dateFrom}/{dateTo}/{key}", method = RequestMethod.GET)
    public String dropAllUser(Model model, @PathVariable(name = "key") String key,
                                         @PathVariable(name = "dateFrom") String dateFrom,
                                         @PathVariable(name = "dateTo") String dateTo) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String keyLog = LocalDateTime.now().format(formatter);
        ociService.log.put(keyLog+"_Pending", Collections.emptyList());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "ja");
        jobScheduler.enqueue(() -> deleteUserTemPoraryRunnable.deleteAllUser(dateFrom, dateTo, key, keyLog));
        
        model.addAttribute("header", "Temporary User Registration Deletion");
        model.addAttribute("key", key);
        return "temporary-user-success-page";
    }
    
    @GetMapping(value = "/logTemporaryUser-download/{key}")
    public ResponseEntity<Resource> downloadCsv(@PathVariable(name = "key") String key) throws IOException {
        File downFile = CSV.downLogTemporaryUser(key);
        Resource resource = new FileSystemResource(downFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.attachment().filename(key + ".csv").build());

        long contentLength = downFile.length();

        ResponseEntity<Resource> responseEntity = ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .body(resource);

//        downFile.delete();

        return responseEntity;
    }



//    @GetMapping(value = "/backTemporaryUser/{key}")
//    public String backPage(@PathVariable(name = "key") String key) throws IOException {
//        ociService.dataTemporaryUser.remove(key);
//        return "redirect:/admin/temporaryUser";
//    }
    
    @GetMapping("/deleteLog")
    public String featuredRacesOverseas(Model model, @RequestParam("page") Optional<Integer> page) {
        model.addAttribute("header", "Temporary User Registration Deletion Log");
        int currentPage = page.orElse(1);
        LogListPagingDTO coursePage = ociService.getListLog(PageRequest.of(currentPage - 1, 12));
        
        	model.addAttribute("currentPage", coursePage.getCurrentPage());
            model.addAttribute("countLog", coursePage.getTotalResults());
            model.addAttribute("listLog", coursePage.getLogDeleteTemporary());
            model.addAttribute("totalPages", coursePage.getTotalPage());
            int totalPages = coursePage.getTotalPage();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        return "delete-log-page";
    }
}
