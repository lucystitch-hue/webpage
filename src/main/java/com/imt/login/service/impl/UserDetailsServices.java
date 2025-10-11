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
 *     Corresponding : slide 8
 *     Modified date:  14/12/2023
 *     Description of change:
 *     + update slide 8(remove file local)
 */
package com.imt.login.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imt.login.component.MyService;
import com.imt.login.dto.AppUserCSVDTO;
import com.imt.login.dto.AppUserCSVListPagingDTO;
import com.imt.login.dto.ListTemporaryUserDTO;
import com.imt.login.dto.TemporaryUserDTO;
import com.imt.login.dto.UserLoginDTO;
import com.imt.login.exception.MessageCheck;
import com.imt.login.model.AppUser;
import com.imt.login.model.TokenManagement;
import com.imt.login.repository.AppUserRepository;
import com.imt.login.repository.TokenManagementRepository;
import com.imt.login.utils.TXT;
import com.opencsv.CSVWriter;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsServices {

    @Autowired
    private AppUserRepository appUserDAO;

    @Autowired
    TokenManagementRepository tokenManagementRepository;

    @Autowired
    private ModelMapper mapper;

//    @Autowired
//    OCIService authenticationService;

    /**
     * This line of code is declaring a private instance variable named `listUser` of type `List` that
     * holds objects of type `AppUserDTO`. It is also initializing the variable with an empty `ArrayList`
     * object. This variable can be accessed only within the class where it is declared.
     */
//    private List<AppUserCSVDTO> listUser = new ArrayList<>();

    /**
     * This is a Java method named `csvReader` that takes a `MultipartFile` object as input and returns a
     * list of `AppUserDTO` objects.
     */
//    public List<AppUserCSVDTO> csvReader(MultipartFile multipartFile) throws IOException {
//        listUser.clear();
//        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        multipartFile.transferTo(file.getAbsoluteFile());
//        Map<String, AppUserCSVDTO> detailCSV = CSV.readCSV(file.getPath());
//        file.delete();
//        listUser = convertMapToList(detailCSV);
//        return listUser;
//    }
    public AppUserCSVListPagingDTO getListUser(Pageable pageable, String keyWord) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize+1;
        long totalUser;
        if(keyWord==null) {
        	totalUser= appUserDAO.countByMsyFlagAll();
        }else {
        	totalUser= appUserDAO.countByMsyFlag(keyWord);
        }
        int totalPage = (int) Math.ceil((double) totalUser / pageSize);
        if(totalPage==0) {
        	totalPage=1;
        }
        AppUserCSVListPagingDTO appUserCSVListPagingDTO = new AppUserCSVListPagingDTO();
        List<AppUserCSVDTO> appUserCSVDTOList = new ArrayList<>();
        
        if (keyWord == null) {
        	appUserCSVListPagingDTO.setCurrentPage(currentPage);
        	appUserCSVListPagingDTO.setItemsPerPage(pageSize);
        	appUserCSVListPagingDTO.setLisAppUserCSVDTOs(appUserCSVDTOList);
        	appUserCSVListPagingDTO.setStartIndex(startItem);
        	appUserCSVListPagingDTO.setTotalPage(totalPage);
        	appUserCSVListPagingDTO.setTotalResults((int)totalUser);
            return appUserCSVListPagingDTO;
        }
        List<AppUser> result = appUserDAO.findByMsyFlagAndEmail(keyWord, pageSize, startItem -1);
        
            appUserCSVDTOList = result.stream()
                    .map(product -> mapper.map(product, AppUserCSVDTO.class))
                    .collect(Collectors.toCollection(ArrayList::new));
        
        appUserCSVListPagingDTO.setCurrentPage(currentPage);
    	appUserCSVListPagingDTO.setItemsPerPage(pageSize);
    	appUserCSVListPagingDTO.setLisAppUserCSVDTOs(appUserCSVDTOList);
    	appUserCSVListPagingDTO.setStartIndex(startItem);
    	appUserCSVListPagingDTO.setTotalPage(totalPage);
    	appUserCSVListPagingDTO.setTotalResults((int)totalUser);
        return appUserCSVListPagingDTO;
    }

//    public void clearData() {
//        listUser.clear();
//    }

    /**
     * This is a method that takes a Map of type Long and AppUserDTO as input and converts it into a List of AppUserDTO.
     * It iterates over the entries of the map and adds the values to the list.
     * Finally, it returns the list.
     */
    public List<AppUserCSVDTO> convertMapToList(Map<String, AppUserCSVDTO> map) {
        List<AppUserCSVDTO> listDTO = new ArrayList<>();
        for (Map.Entry<String, AppUserCSVDTO> entry : map.entrySet()) {
            listDTO.add(entry.getValue());
        }
        return listDTO;
    }

    /**
     * This is a method in Java that takes a String parameter `userId` and returns an `AppUser` object. It
     * throws a `ChangeSetPersister.NotFoundException` if the `findById` method of the `appUserDAO` object
     * returns an empty optional. The `orElseThrow` method is used to throw the exception if the optional
     * is empty.
     */
    public AppUser findUser(String userId) throws ChangeSetPersister.NotFoundException {
        return appUserDAO.findById(userId).get();
    }

    public AppUserCSVDTO addUser(AppUser appUser) {
        return mapper.map(appUserDAO.save(appUser), AppUserCSVDTO.class);
    }

    public List<AppUserCSVDTO> findAllUser() {
        return appUserDAO.findAll().stream()
                .filter(appUser -> StringUtils.isNotBlank(appUser.getMsyFlag()) && appUser.getMsyFlag().equals("1"))
                .map(user -> mapper.map(user, AppUserCSVDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

//    public List<AppUser> findAll() {
//        return appUserDAO.findAllByMsyflg(,1000);
//    }
    public File downloadCSV() {
        File tempFile = null;
        FileWriter fileWriter = null;
        try {
            tempFile = File.createTempFile("ListUser", ".csv");
            fileWriter = new FileWriter(tempFile, false);

            CSVWriter csvWriter = new CSVWriter(fileWriter);
            try {
                List<AppUser> findAll = appUserDAO.findAllByMsyflg();
                findAll.stream().forEach(obj->{
                    String[] userData = {
                            obj.getUserId(),
                            obj.getSei(),
                            obj.getMei(),
                            obj.getSeiKana(),
                            obj.getMeiKana(),
                            obj.getBirthday(),
                            obj.getEmail(),
                            obj.getPostcode(),
                            obj.getSex(),
                            obj.getJob(),
                            obj.getStartYear(),
                            obj.getUserService(),
                            obj.getUserReason(),
                            obj.getUserDayOff(),
                            obj.getIpatId1(),
                            obj.getYsnFlag1(),
                            obj.getIpatId2(),
                            obj.getYsnFlag2(),
                            obj.getRenFlag(),
                            obj.getRenSns(),
                            obj.getYear20(),
                            obj.getPushTorikeshi(),
                            obj.getPushJogai(),
                            obj.getPushKisyu(),
                            obj.getPushJikoku(),
                            obj.getPushBaba(),
                            obj.getPushWeather(),
                            obj.getPushUmaWeight(),
                            obj.getPushHarai(),
                            obj.getPushUtokubetsu(),
                            obj.getPushMokuden(),
                            obj.getPushYokuden(),
                            obj.getPushThisWeek(),
                            obj.getPushNews(),
                            obj.getPushYobi1(),
                            obj.getPushYobi2(),
                            obj.getUserKbn(),
                            obj.getMsyFlag(),
                            obj.getWithdrawReason1(),
                            obj.getWithdrawReason2(),
                            obj.getWithdrawReasonText(),
                            obj.getLatestLogin(),
                            obj.getInitialYmd(),
                            obj.getUpdateTimestamp() != null ? obj.getUpdateTimestamp().toString() : "null",
                            obj.getPendingEmail(),
                            obj.getUmacaFlg()};
                    csvWriter.writeNext(userData);
                });
            
            }catch (Exception e) {
                csvWriter.flush();
                csvWriter.close();
            }
            csvWriter.flush();
            csvWriter.close();

            return tempFile;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public AppUserCSVDTO editUser(String userId, AppUser appUser, List<Boolean> uService, List<Boolean> uReason, List<Boolean> uDayOff) throws FirebaseMessagingException, ChangeSetPersister.NotFoundException {
        try {
        	
//        	String urlApiPostCode = "https://zipcloud.ibsnet.co.jp/api/search?zipcode="+appUser.getPostcode();

//            String getPostCode = RestService.getRest(urlApiPostCode, null);
//            boolean checkPostCode = checkPostCode(getPostCode);
//        	if(!checkPostCode) {
//        		return null;
//        	}
            Gson gson = new GsonBuilder().create();
            String uServiceBuffer = "";
            String element1;
            for (int i = 0; i < uService.size(); i++) {
                if (uService.get(i) == false) {
                    element1 = "0";

                } else {
                    element1 = "1";
                }
                uServiceBuffer += element1;
            }


            String uReasonBuffer = "";
            String element2;
            for (int i = 0; i < uReason.size(); i++) {
                if (uReason.get(i) == false) {
                    element2 = "0";

                } else {
                    element2 = "1";
                }
                uReasonBuffer += element2;
            }

            String uDayOffBuffer = "";
            String element3;
            for (int i = 0; i < uDayOff.size(); i++) {
                if (uDayOff.get(i) == false) {
                    element3 = "0";

                } else {
                    element3 = "1";
                }
                uDayOffBuffer += element3;
            }
            AppUser user = findUser(userId);
//            user.setPassword(appUser.getPassword());
//            user.setMei(appUser.getMei());
//            user.setSei(appUser.getSei());
//            user.setMeiKana(appUser.getMeiKana());
//            user.setSeiKana(appUser.getSeiKana());
//            user.setEmail(appUser.getEmail());
//            user.setBirthday(appUser.getBirthday());
            if(appUser.getSex()!=null && !appUser.getSex().equals(user.getSex())) {
            	user.setSex(appUser.getSex());
            }
            if(appUser.getJob() !=null && !appUser.getJob().equals(user.getJob())) {
            	user.setJob(appUser.getJob());
            }
            if(appUser.getStartYear() !=null && !appUser.getStartYear().equals(user.getStartYear())) {
            	user.setStartYear(appUser.getStartYear());
            }
            if(appUser.getYear20() !=null && !appUser.getYear20().equals(user.getYear20())) {
            	user.setYear20(appUser.getYear20());
            }
            if(appUser.getPostcode() !=null && !appUser.getPostcode().equals(user.getPostcode())) {
            	user.setPostcode(appUser.getPostcode());
            }
            if(uServiceBuffer !=null && !uServiceBuffer.equals(user.getUserService().trim())) {
            	user.setUserService(uServiceBuffer);
            }
            if(uReasonBuffer !=null && !uReasonBuffer.equals(user.getUserReason().trim())) {
            	user.setUserReason(uReasonBuffer);
            }
            if(uDayOffBuffer !=null && !uDayOffBuffer.equals(user.getUserDayOff().trim())) {
            	user.setUserDayOff(uDayOffBuffer+"  ");
            }
            
            if(appUser.getIpatId1() != null && appUser.getIpatId1().length()==8) {
            	user.setIpatId1(appUser.getIpatId1());
            }
            if(appUser.getIpatId2() != null && appUser.getIpatId2().length()==8) {
            	user.setIpatId2(appUser.getIpatId2());
            }
//            user.setIpatId1(appUser.getIpatId1());
//            user.setIpatPars1(appUser.getIpatPars1());
//            user.setIpatPass1(appUser.getIpatPass1());
//            user.setIpatId2(appUser.getIpatId2());
//            user.setIpatPars2(appUser.getIpatPars2());
//            user.setIpatPass2(appUser.getIpatPass2());
            if(appUser.getInitialYmd() != null && !appUser.getInitialYmd().equals(user.getInitialYmd())) {
            	user.setInitialYmd(appUser.getInitialYmd());
            }
            if(appUser.getLatestLogin() != null && !appUser.getLatestLogin().equals(user.getLatestLogin())) {
            	user.setLatestLogin(appUser.getLatestLogin());
            }
            
            user.setUpdateTimestamp(LocalDateTime.now());
            if(appUser.getUserKbn() != null && !appUser.getUserKbn().equals(user.getUserKbn())) {
            	user.setUserKbn(appUser.getUserKbn());
            }
            if(appUser.getUmacaFlg() != null && !appUser.getUmacaFlg().equals(user.getUmacaFlg())) {
            	user.setUmacaFlg(appUser.getUmacaFlg());
            }
            
            if (appUser.getYsnFlag1() != null && appUser.getYsnFlag1().equals("on")) {
                user.setYsnFlag1("1");
                
            }else{
            	user.setYsnFlag1("0");
            }
            if (appUser.getYsnFlag2() != null && appUser.getYsnFlag2().equals("on")) {
                user.setYsnFlag2("1");
                
            }else {
            	user.setYsnFlag2("0");
            }
            
            AppUser appUser1 = appUserDAO.save(user);
            AppUserCSVDTO appUserCSVDTO = mapper.map(appUser1, AppUserCSVDTO.class);

            return appUserCSVDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkPostCode(String getPostCode) {
    	JsonObject jsonObject = JsonParser.parseString(getPostCode).getAsJsonObject();
    	if(jsonObject.get("results").isJsonNull()) {
    		return false;
    	}
    	return true;
	}

	public void dropUser(String userId) {
        AppUser appUser = appUserDAO.findById(userId).get();
        appUser.setMsyFlag("2");
        appUserDAO.save(appUser);
    }

	public boolean checkUserNotice(String username) {
		ClassPathResource resource = new ClassPathResource(MyService.userNotification());
		String pathFile;
		try {
			pathFile = String.valueOf(resource.getFile().toPath());
			String decodedUri = URLDecoder.decode(username, StandardCharsets.UTF_8.toString());
			Optional<UserLoginDTO> userLoginDTOList = TXT.readTxtLogin(pathFile).stream()
					.filter(userLoginDTO -> userLoginDTO.getUsername().equals(decodedUri)).findAny();
			if (userLoginDTOList.isPresent()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkUserAdmin(String username) {
		ClassPathResource resource = new ClassPathResource(MyService.adminUser());
		String pathFile;
		try {
			pathFile = String.valueOf(resource.getFile().toPath());
			String decodedUri = URLDecoder.decode(username, StandardCharsets.UTF_8.toString());
			Optional<UserLoginDTO> userLoginDTOList = TXT.readTxtLogin(pathFile).stream()
					.filter(userLoginDTO -> userLoginDTO.getUsername().equals(decodedUri)).findAny();
			if (userLoginDTOList.isPresent()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


}
