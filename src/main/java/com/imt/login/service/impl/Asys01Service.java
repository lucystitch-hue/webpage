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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imt.login.dto.AppUserCSVDTO;
import com.imt.login.dto.AppUserCSVListPagingDTO;
import com.imt.login.dto.Asys01ListPagingDTO;
import com.imt.login.model.AppUser;
import com.imt.login.model.Asys01;
import com.imt.login.repository.Asys01Repository;

@Service
public class Asys01Service {
	@Autowired
	Asys01Repository asys01Repository;

	public Asys01ListPagingDTO getListAsys01(Pageable pageable) {
		int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize+1;
        long totalAsys01 = asys01Repository.countAsys01();
        
        int totalPage = (int) Math.ceil((double) totalAsys01 / pageSize);
        
        if(totalPage==0) {
        	totalPage=1;
        }
        Asys01ListPagingDTO asys01ListPagingDTO = new Asys01ListPagingDTO();
        List<Asys01> result = asys01Repository.findAllAsys01( pageSize, startItem-1);
        
        asys01ListPagingDTO.setCurrentPage(currentPage);
    	asys01ListPagingDTO.setItemsPerPage(pageSize);
    	asys01ListPagingDTO.setListAsys01s(result);
    	asys01ListPagingDTO.setStartIndex(startItem);
    	asys01ListPagingDTO.setTotalPage(totalPage);
    	asys01ListPagingDTO.setTotalResults((int)totalAsys01);
        return asys01ListPagingDTO;
	}

}
