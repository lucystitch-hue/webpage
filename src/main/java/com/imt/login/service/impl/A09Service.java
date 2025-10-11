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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imt.login.dto.A09DTO;
import com.imt.login.dto.A09ListPagingDTO;
import com.imt.login.dto.AppUserCSVDTO;
import com.imt.login.model.A09;
import com.imt.login.model.A09ManagementPK;
import com.imt.login.repository.A09Repository;

@Service
public class A09Service {
	@Autowired
	A09Repository a09Repository;
	
	@Autowired
    private ModelMapper mapper;

	public A09ListPagingDTO getListA09(Pageable pageable) {
		int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        int endItem = startItem + pageSize;
        long totalA09 = a09Repository.countA09();
        
        int totalPage = (int) Math.ceil((double) totalA09 / pageSize);
        
        if(totalPage==0) {
        	totalPage=1;
        }
        A09ListPagingDTO a09ListPagingDTO = new A09ListPagingDTO();
        List<A09> result = a09Repository.findAllA09(startItem+1, endItem);
        List<A09DTO> mapResult = result.stream()
        		.map(A09DTO::convertToDTO)
        	    .collect(Collectors.toList());

        a09ListPagingDTO.setCurrentPage(currentPage);
    	a09ListPagingDTO.setItemsPerPage(pageSize);
    	a09ListPagingDTO.setListA09(mapResult);
    	a09ListPagingDTO.setStartIndex(startItem);
    	a09ListPagingDTO.setTotalPage(totalPage);
    	a09ListPagingDTO.setTotalResults((int)totalA09);
        return a09ListPagingDTO;
	}

	public A09 confirma09(String kaisaiYmd, String raceName, String grd, String startDate, String endDate) {
		A09ManagementPK keyA09 = new A09ManagementPK(kaisaiYmd.replace("-", ""), raceName);
		A09 a09 = new A09( keyA09, grd, startDate, endDate);
		return a09;
	}

	public void createA09(String kaisaiYmd, String raceName, String grd, String startDate, String endDate) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(startDate, inputFormatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        A09ManagementPK keyA09 = new A09ManagementPK(kaisaiYmd , raceName);
		A09 a09 = new A09( keyA09, grd, dateTime1.format(outputFormatter), dateTime2.format(outputFormatter));
		a09Repository.save(a09);
		
	}

	public A09 findById(String kaisaiYmd, String raceNameEncode) {
		byte[] raceNameByte = Base64.getDecoder().decode(raceNameEncode.replace("slash", "/"));
		String raceName = new String(raceNameByte);
		String kaisaiYmdDb = kaisaiYmd.replace("%20", " ");
		A09 a09 = a09Repository.findByIdA09(kaisaiYmdDb.replace("-", ""), raceName);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		if(a09.getStartDate().trim().length()==12) {
			LocalDateTime dateTime1 = LocalDateTime.parse(a09.getStartDate().trim(), inputFormatter1);
			a09.setStartDate(dateTime1.format(outputFormatter1));
		}
		if(a09.getEndDate().trim().length()==12) {
			LocalDateTime dateTime2 = LocalDateTime.parse(a09.getEndDate().trim(), inputFormatter1);
			a09.setEndDate(dateTime2.format(outputFormatter1));
		}
		LocalDate dateTime3 = LocalDate.parse(a09.getPrimaryKeys().getKaisaiYmd().trim(), inputFormatter);
		a09.getPrimaryKeys().setKaisaiYmd(dateTime3.format(outputFormatter));
		return a09;
	}

	@Transactional
	public void editA09(String kaisaiYmd, String raceName, String grd, String startDate, String endDate, String kaisaiYmdOriginal,String raceNameOriginal) {
		String kaisaiYmdDb = kaisaiYmd.replace("%20", " ");
		String kaisaiYmdOriginalDb = kaisaiYmdOriginal.replace("%20", " ");
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime1 = LocalDateTime.parse(startDate, inputFormatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(endDate, inputFormatter);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String name=raceNameOriginal;
		String kai= kaisaiYmdOriginalDb.replace("-", "");
		if (!kaisaiYmdOriginal.equals(kaisaiYmd)) {
			kai= kaisaiYmdDb.replace("-", "");
			a09Repository.saveA09EditKai(kaisaiYmdOriginalDb.replace("-", ""),raceNameOriginal,kaisaiYmdDb.replace("-", ""));
		}
		if (!raceNameOriginal.equals(raceName)) {
			name= raceName;
			a09Repository.saveA09EditName(kai,raceNameOriginal,name);
		}
		a09Repository.saveA09Edit(kai,name, dateTime2.format(outputFormatter),grd,dateTime1.format(outputFormatter),Timestamp.from(Instant.now()));
	}

	public void delete(String kaisaiYmd, String name) {
		String kaisaiYmdDb = kaisaiYmd.replace("%20", " ");
		A09 a09 = a09Repository.findByIdA09(kaisaiYmdDb.replace("-", ""),name);
		a09Repository.delete(a09);;
	}

	public boolean checkKey(String key, String name) {
		A09 a09 = a09Repository.findByIdA09(key, name);
		if(a09 != null && a09.getPrimaryKeys().getKaisaiYmd().equals(key) && a09.getPrimaryKeys().getRaceName().equals(name) ) {
			return false;
		}
		return true;
	}

	public boolean checkKeyEdit(String key, String name, String keyOriginal, String nameOriginal) {
		A09 a09Edit = a09Repository.findByIdA09(key,name);
		if((!keyOriginal.equals(key) || !nameOriginal.equals(name)) && a09Edit!=null) {
			return false;
		}
		
		return true;
	}
}
