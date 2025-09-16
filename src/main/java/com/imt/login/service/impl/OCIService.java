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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imt.login.constan.ConstansUser;
import com.imt.login.dto.A09ListPagingDTO;
import com.imt.login.dto.AppUserDto;
import com.imt.login.dto.ListTemporaryUserDTO;
import com.imt.login.dto.LogListPagingDTO;
import com.imt.login.dto.TemporaryUserDTO;
import com.imt.login.exception.MessageCheck;
import com.imt.login.exception.MessageData;
import com.imt.login.model.A09;
import com.imt.login.model.AppUser;
import com.imt.login.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.imt.login.dto.ListTemporaryUserDTO;
import com.imt.login.dto.TemporaryUserDTO;
import com.imt.login.exception.MessageCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
public class OCIService {

    public static final String $_ENV_OCI_API_HOST = "${env.oci_api_host}";
    public static final String $_ENV_OCI_LOGIN_CLIENT_SECRET = "${env.oci_login_client_secret}";
    public static final String $_ENV_OCI_LOGIN_CLIENT_ID = "${env.oci_login_client_id}";
    public static final String $_ENV_OCI_REGISTER_CLIENT_SECRET = "${env.oci_register_client_secret}";
    public static final String $_ENV_OCI_REGISTER_CLIENT_ID = "${env.oci_register_client_id}";
    public static final String OAUTH2_V1_TOKEN = "/oauth2/v1/token";
    public static final String BASIC = "Basic ";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String APPLICATION_JSON = "application/json";
    public static final String BEARER = "Bearer ";
    @Value($_ENV_OCI_REGISTER_CLIENT_ID)
    private String registerClientId;

    @Value($_ENV_OCI_REGISTER_CLIENT_SECRET)
    private String registerClientSecret;

    @Value($_ENV_OCI_LOGIN_CLIENT_ID)
    private String loginClientId;

    @Value($_ENV_OCI_LOGIN_CLIENT_SECRET)
    private String loginClientSecret;

    @Value($_ENV_OCI_API_HOST)
    private String ociApiHost;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AppUserRepository appUserDAO;

    ListTemporaryUserDTO temporaryUser = new ListTemporaryUserDTO();

    public Map<String, List<TemporaryUserDTO>> dataTemporaryUser = new HashMap<>();
    public Map<String, List<TemporaryUserDTO>> log = new HashMap<>();
    
    public Map<String, String> headerGetToken(){
        String userCredentials = registerClientId + ":" + registerClientSecret;
        String basicAuth = BASIC
                + Base64.getEncoder().encodeToString(userCredentials.getBytes(StandardCharsets.UTF_8));
        Map<String, String> header = new HashMap<>();
        header.put(AUTHORIZATION, basicAuth);
        header.put(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
        header.put("Accept-Language" ,"ja");
        return header;
    }

    public MessageCheck accessTokenRegister() {
        try {
            String url = ociApiHost + OAUTH2_V1_TOKEN;
            return RestService.getTokenPostRest(url, headerGetToken());
        } catch (Exception e) {
            return new MessageCheck(false, e.getMessage(), null);
        }
    }

    public Map<String, String> header(String token){
        Map<String, String> header = new HashMap<>();
        header.put(AUTHORIZATION, BEARER + token);

        return header;
    }

    public ListTemporaryUserDTO listTemporaryUser(String response) {
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        int totalResults = jsonObject.getAsJsonPrimitive("totalResults").getAsInt();
        int startIndex = jsonObject.getAsJsonPrimitive("startIndex").getAsInt();
        int itemsPerPage = jsonObject.getAsJsonPrimitive("itemsPerPage").getAsInt();

        ListTemporaryUserDTO listTemporaryUserDTO = new ListTemporaryUserDTO();
        listTemporaryUserDTO.setTotalResults(totalResults);
        listTemporaryUserDTO.setStartIndex(startIndex);
        listTemporaryUserDTO.setItemsPerPage(itemsPerPage);

        JsonArray resourcesArray = jsonObject.getAsJsonArray("Resources");
        List<TemporaryUserDTO> temporaryUserDTOList = new ArrayList<>();

        for (JsonElement resourceValue : resourcesArray) {
            TemporaryUserDTO temporaryUserDTO = new TemporaryUserDTO();
            JsonObject resourceObject = resourceValue.getAsJsonObject();
            JsonObject createdElementMeta = resourceObject.get("meta").getAsJsonObject();
            JsonElement createdElement = createdElementMeta.getAsJsonPrimitive("created");
            JsonElement userNameElement = resourceObject.get("userName");
            JsonElement idElement = resourceObject.get("id");

            String created = (createdElement != null && createdElement.isJsonPrimitive()) ?
                    createdElement.getAsJsonPrimitive().getAsString() : null;

            String userName = (userNameElement != null && userNameElement.isJsonPrimitive()) ?
                    userNameElement.getAsJsonPrimitive().getAsString() : null;

            String id = (idElement != null && idElement.isJsonPrimitive()) ?
                    idElement.getAsJsonPrimitive().getAsString() : null;

            temporaryUserDTO.setUserId(id);
            temporaryUserDTO.setEmail(userName.replace("'","`"));
            temporaryUserDTO.setCreated(created);
            temporaryUserDTO.setSuccessDelete(false);
            temporaryUserDTOList.add(temporaryUserDTO);
        }

        listTemporaryUserDTO.setTemporaryUserDTO(temporaryUserDTOList);
        return listTemporaryUserDTO;
    }

    public ListTemporaryUserDTO getListTemporaryUser(String dateFrom, String dateTo, Pageable pageable) {
        try {
            MessageCheck accessTokenRegister = accessTokenRegister();
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = (currentPage * pageSize)+1;
            if(startItem==2){
                startItem =1;
            }

            LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime dateTimeFromMinus9Hours = dateTimeFrom.minusHours(9);
            LocalDateTime dateTimeToMinus9Hours = dateTimeTo.minusHours(9);
            DateTimeFormatter desiredFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String formattedDateTimeFrom = dateTimeFromMinus9Hours.format(desiredFormatter);
            String formattedDateTimeTo = dateTimeToMinus9Hours.format(desiredFormatter);
            String urlGetTemporaryUser = ociApiHost + "/admin/v1/Users?attributes=meta.created&filter=meta.created+gt+%22"+formattedDateTimeFrom+"%22+and+meta.created+lt+%22"
                    +formattedDateTimeTo+"%22+and+not%28urn:ietf:params:scim:schemas:oracle:idcs:extension:userState:User:lastSuccessfulLoginDate+pr%29&count="
                    +pageSize+"&sortBy=meta.created&sortOrder=descending&startIndex="+startItem;

            String responseTemporaryUser = RestService.getRest(urlGetTemporaryUser, header(accessTokenRegister.getData()));
            ListTemporaryUserDTO listTemporaryUsers = listTemporaryUser(responseTemporaryUser);

           int totalPage = (int) Math.ceil(Double.parseDouble(String.valueOf(listTemporaryUsers.getTotalResults())) / pageSize);

            listTemporaryUsers.setTotalPage(totalPage);
            listTemporaryUsers.setCurrentPage(currentPage);
            return listTemporaryUsers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MessageData deleteUserItem(String userId, String token) {
        try {
            // url OCI delete user
            String urlDelete = ociApiHost + "/admin/v1/Users/" + userId + "?forceDelete=true";
            MessageData deleteUser = RestService.dropUserDeleteRest(urlDelete, token);
            if (!deleteUser.getSuccess()) {
                return deleteUser;
            }
            return deleteUser;
        } catch (Exception e) {
            return new MessageData(false, e.getMessage(), null);
        }
    }

    public LogListPagingDTO getListLog(Pageable pageable) {
    	try {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        List<String> logKeys = new ArrayList<>(log.keySet());
        List<String> listLogFiles = new ArrayList<>();
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
        String pathFile =tempDir.toString();
		File folder2 = new File(pathFile);
		File[] files = folder2.listFiles();        
			if (files != null) {
				for (String s : logKeys) {
					if (s.contains("_Pending")) {
						listLogFiles.add(s);
					}
				}
				for (File file : files) {
					if (file.isFile() && file.getName().length() == 16 && file.getName().endsWith(".csv")) {
						listLogFiles.add(file.getName().replace(".csv", ""));

					}
				}
			}
        
        long totalLog = listLogFiles.size();
        
        int totalPage = (int) Math.ceil((double) totalLog / pageSize);
        if (totalPage == 0) {
            totalPage = 1;
        }
        
        if (currentPage >= totalPage) {
            // Adjust currentPage if it exceeds totalPage to prevent out-of-bounds errors
            currentPage = Math.max(0, totalPage - 1);
        }
        
        int startItem = currentPage * pageSize;
        int toIndex = Math.min(startItem + pageSize, (int) totalLog);
        List<String> result = listLogFiles.subList(startItem, toIndex);
        
        LogListPagingDTO logListPaginsgDTO = new LogListPagingDTO();
        logListPaginsgDTO.setCurrentPage(currentPage);
        logListPaginsgDTO.setItemsPerPage(pageSize);
        logListPaginsgDTO.setLogDeleteTemporary(result);
        logListPaginsgDTO.setStartIndex(startItem);
        logListPaginsgDTO.setTotalPage(totalPage);
        logListPaginsgDTO.setTotalResults((int) totalLog);
        
        return logListPaginsgDTO;
    	}catch (Exception e) {
    		deleteKeysContainingSubstring(log,"_Pending");
    		return null;
		}
    }
    
    private static void deleteKeysContainingSubstring(Map<String, List<TemporaryUserDTO>> map, String substring) {
        Iterator<Map.Entry<String, List<TemporaryUserDTO>>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<TemporaryUserDTO>> entry = iterator.next();
            if (entry.getKey().contains(substring)) {
                iterator.remove();
            }
        }
    }
}
