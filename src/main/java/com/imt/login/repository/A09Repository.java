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
package com.imt.login.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imt.login.model.A09;
import com.imt.login.model.A09ManagementPK;


@Repository
public interface A09Repository extends JpaRepository<A09, A09ManagementPK> {
	
		@Query(value = " ", nativeQuery = true)
	int countAll();
	
	@Query(value = "", nativeQuery = true)
	A09 findByIdA09(@Param("kai") String kai, @Param("name") String name );
	
	@Query(value = " ", nativeQuery = true) 
	List<A09> findAllA09(@Param("startItem") int startItem, @Param("endItem") int endItem);
	
	@Modifying
	@Query(value= "", nativeQuery = true)
	void saveA09Edit(String kaiO, String nameO, String endDate, String grd, String startDate, Timestamp updateTime );
	
	@Modifying
	@Query(value= "", nativeQuery = true)
	void saveA09EditKai(String kaiO, String nameO, String kai);
	
	@Modifying
	@Query(value= "", nativeQuery = true)
	void saveA09EditName(String kaiO, String nameO, String name);
}
