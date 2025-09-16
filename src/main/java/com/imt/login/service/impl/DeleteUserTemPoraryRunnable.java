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
 *     Modified date:  14/12/2023
 *     Corresponding : slide 37
 *     Description of change:
 *      -  + update side 37(hanlde function delete temporary)
 */
package com.imt.login.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imt.login.dto.ListTemporaryUserDTO;
import com.imt.login.dto.TemporaryUserDTO;
import com.imt.login.exception.MessageData;
import com.imt.login.utils.CSV;
import com.mysql.cj.xdevapi.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeleteUserTemPoraryRunnable {


	@Autowired
    OCIService ociService;
    

    

    public void run(String userId, String created, String email) {
        try {
            TemporaryUserDTO temporaryUserDTO = new TemporaryUserDTO();
            temporaryUserDTO.setUserId(userId);
            temporaryUserDTO.setCreatedFormat(created);
            temporaryUserDTO.setEmail(email);
            temporaryUserDTO.setSuccessDelete(false);

            MessageData deleteUser = this.ociService.deleteUserItem(userId, this.ociService.accessTokenRegister().getData());
            if (deleteUser.getSuccess()) {
                temporaryUserDTO.setSuccessDelete(true);
            }
            List<TemporaryUserDTO> userTemporary = new ArrayList<>();
            userTemporary.add(temporaryUserDTO);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            String key = LocalDateTime.now().format(formatter);
            
            ociService.log.put(key, userTemporary);
            CSV.exportLogTemporaryUser(userTemporary, key);
            ociService.dataTemporaryUser.put(userId,userTemporary);

        } catch (Exception e) {
        }
    }

    public void deleteAllUser(String dateFrom, String dateTo,String key, String keyLog) {
        try {
            int size = 1000;
            int page = 0;
            
            ListTemporaryUserDTO coursePage = ociService.getListTemporaryUser(dateFrom,dateTo, PageRequest.of(page, size));
            List<TemporaryUserDTO> temporaryUserDTOList = new ArrayList<>();
//            temporaryUserDTOList.addAll(coursePage.getTemporaryUserDTO());
            for(int i=0; i< coursePage.getTotalPage(); ++i){
                ListTemporaryUserDTO coursePageNext = ociService.getListTemporaryUser(dateFrom,dateTo, PageRequest.of(i, size));
                temporaryUserDTOList.addAll(coursePageNext.getTemporaryUserDTO());
            }
            
            for (TemporaryUserDTO user : temporaryUserDTOList) {
                MessageData deleteUser = this.ociService.deleteUserItem(user.getUserId(), this.ociService.accessTokenRegister().getData());
                if(deleteUser.getSuccess()){
                    user.setSuccessDelete(true);
                }
            }
            if (ociService.log.containsKey(keyLog+"_Pending")) {
            ociService.log.remove(keyLog+"_Pending");
            ociService.log.put(keyLog,temporaryUserDTOList);
            CSV.exportLogTemporaryUser(temporaryUserDTOList, keyLog);
            }
            
            ociService.dataTemporaryUser.put(key,temporaryUserDTOList);
        } catch (Exception e) {
        }
    }
}
