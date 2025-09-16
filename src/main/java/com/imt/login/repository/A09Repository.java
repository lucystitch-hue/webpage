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
	
		@Query(value = "SELECT COUNT(*) FROM TEST3.A09 A09 ", nativeQuery = true)
	int countAll();
	
	@Query(value = "select * from test3.a09 a09x0_ where a09x0_.kaisaiymd= :kai and a09x0_.racename=:name", nativeQuery = true)
	A09 findByIdA09(@Param("kai") String kai, @Param("name") String name );
	
	@Query(value = " SELECT *\r\n" + 
			"FROM (\r\n" + 
			"    SELECT \r\n" + 
			"        A09.*,\r\n" + 
			"        ROW_NUMBER() OVER (ORDER BY A09.KAISAIYMD DESC,A09.UPDTIMESTAMP DESC,A09.RACENAME DESC) AS row_num\r\n" + 
			"    FROM TEST3.A09 A09\r\n" + 
			") \r\n" + 
			"WHERE row_num BETWEEN :startItem AND :endItem", nativeQuery = true) 
	List<A09> findAllA09(@Param("startItem") int startItem, @Param("endItem") int endItem);
	
	@Modifying
	@Query(value= "update TEST3.A09 A09 set "
			+ " A09.ENDDATE = :endDate,"
			+ " A09.GRD = :grd,"
			+ " A09.STARTDATE = :startDate,"
			+ " A09.UPDTIMESTAMP = :updateTime WHERE A09.kaisaiymd= :kaiO and A09.racename=:nameO", nativeQuery = true)
	void saveA09Edit(String kaiO, String nameO, String endDate, String grd, String startDate, Timestamp updateTime );
	
	@Modifying
	@Query(value= "update TEST3.A09 A09 set "
			+ "A09.KAISAIYMD = :kai WHERE A09.kaisaiymd= :kaiO and A09.racename=:nameO", nativeQuery = true)
	void saveA09EditKai(String kaiO, String nameO, String kai);
	
	@Modifying
	@Query(value= "update TEST3.A09 A09 set "
			+ " A09.RACENAME = :name WHERE A09.kaisaiymd= :kaiO and A09.racename=:nameO", nativeQuery = true)
	void saveA09EditName(String kaiO, String nameO, String name);
}
