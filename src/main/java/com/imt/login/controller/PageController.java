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

///*     Summary of file  to comment on:
// *     Modified date:  17/11/2023
// *     Corresponding : slide 11
// *     Description of change:
// *      - add produces = "application/json;charset=UTF-8"
// */
//package com.imt.login.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.imt.login.dto.TemporaryUserDTO;
//import com.imt.login.exception.MessageCheck;
//import com.imt.login.exception.MessageData;
//import com.imt.login.model.SampleViewModel;
//import com.imt.login.service.impl.*;
//
//import com.imt.login.utils.CSV;
//import org.apache.tomcat.util.http.HeaderUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ContentDisposition;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/admin")
//@CrossOrigin
//public class PageController {
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    OCIService authenticationService;
//
//    @GetMapping("/temporaryUserSuccessDelete/{key}")
//    public String temporaryUserSuccessDelete(@PathVariable(name = "key") String key, Model model) {
//        model.addAttribute("header", "Temporary User Registration Deletion");
//        model.addAttribute("key", key);
//        List<TemporaryUserDTO> temporaryUserDTOList = authenticationService.dataTemporaryUser.get(key);
//        if(temporaryUserDTOList == null){
//            return "redirect:/admin/temporaryUser";
//        }
//        int countListAll = authenticationService.dataTemporaryUser.get(key).size();
//        Long countSuccess = authenticationService.dataTemporaryUser.get(key)
//                .stream().filter(i->i.isSuccessDelete()).count();
//        Long countNotSuccess = authenticationService.dataTemporaryUser.get(key)
//                .stream().filter(i->!i.isSuccessDelete()).count();
//        model.addAttribute("countNotSuccess", countNotSuccess);
//        model.addAttribute("countSuccess", countSuccess);
//        model.addAttribute("countAll", countListAll);
//        return "temporary-user-success-page";
//    }
//
//    @RequestMapping(value = "/deleteUser/{userId}/{email}/{created}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public ResponseEntity<?> dropUser(@PathVariable(name = "userId") String userId,
//                                      @PathVariable(name = "email") String email,
//                                      @PathVariable(name = "created") String created) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept-Language", "ja");
//        new DeleteUserTemPoraryRunnable( objectMapper,authenticationService,userId,email,created).run();
//        return ResponseEntity.ok().headers(headers).body("");
//    }
//
//    @RequestMapping(value = "/deleteAllUser/{dateFrom}/{dateTo}/{key}", method = RequestMethod.GET)
//    public ResponseEntity<?> dropAllUser(@PathVariable(name = "key") String key,
//                                         @PathVariable(name = "dateFrom") String dateFrom,
//                                         @PathVariable(name = "dateTo") String dateTo) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept-Language", "ja");
//        new DeleteUserTemPoraryRunnable(objectMapper, authenticationService, null,null,null)
//                .deleteAllUser(dateFrom, dateTo, key);
//        return ResponseEntity.ok().headers(headers).body("");
//    }
//
//    @GetMapping(value = "/logTemporaryUser-download/{key}")
//    public ResponseEntity<Resource> downloadCsv(@PathVariable(name = "key") String key) throws IOException {
//        // prepare file resource to download
//        List<TemporaryUserDTO> temporaryUserDTOList = authenticationService.dataTemporaryUser.get(key);
//        if (temporaryUserDTOList == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        Resource resource = new FileSystemResource(CSV.downLogTemporaryUser(temporaryUserDTOList, key));
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_PLAIN);
//        headers.setContentDisposition(ContentDisposition.attachment().filename(resource.getFilename()).build());
//
//        // return file resource for download
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(resource.contentLength())
//                .body(resource);
//    }
//
//    @GetMapping(value = "/backTemporaryUser/{key}")
//    public String backPage(@PathVariable(name = "key") String key) throws IOException {
//        // prepare file resource to download
//        List<TemporaryUserDTO> temporaryUserDTOList = authenticationService.dataTemporaryUser.remove(key);
//
//        return "redirect:/admin/temporaryUser";
//    }
//}
