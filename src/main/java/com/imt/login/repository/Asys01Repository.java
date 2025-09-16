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

import com.imt.login.model.AppUser;
import com.imt.login.model.Asys01;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Asys01Repository extends JpaRepository<Asys01, String> {

    @Query(value = "SELECT * FROM TEST3.ASYS01 ASYS01 WHERE ASYS01.SYS01KEYCODE =0012", nativeQuery = true)
    Asys01 findAllByKeyCode();

	@Query(value = "SELECT COUNT(*) FROM TEST3.ASYS01 ASYS01 ", nativeQuery = true)
	Long countAsys01();
	
	@Query(value = " SELECT * FROM TEST3.ASYS01 ASYS01 ORDER BY ASYS01.SYS01KEYCODE OFFSET :startIndex ROWS FETCH NEXT :itemsPerPage ROWS ONLY ", nativeQuery = true) 
	List<Asys01> findAllAsys01(@Param("itemsPerPage") int itemsPerPage, @Param("startIndex") int startIndex);
	
}
