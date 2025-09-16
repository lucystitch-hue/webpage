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

import com.imt.login.dto.FcmTokenUserDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,String> {

	@Query(nativeQuery = true)
	List<FcmTokenUserDTO> getUsersToPushNotification(@Param("category") String category, @Param("target") String target);


	@Query(value = "SELECT DISTINCT A01.USERID "
			+ "FROM TEST3.A01 A01 "
			+ "WHERE A01.MSYFLG = '1' AND "
			+ "(TRIM(:category) IS NULL " +
			"OR :category NOT IN ('90', '91', '10', '11', '12', '13', '19', '20', '50', '51', '52', '53', '54', '61', '62', '63', '64') " +
			"OR (:category = '10' AND A01.PUSHTORIKESHI = '1') " +
			"OR (:category = '11' AND A01.PUSHJOGAI = '1') " +
			"OR (:category = '12' AND A01.PUSHKISYU = '1') " +
			"OR (:category = '13' AND A01.PUSHJIKOKU = '1') " +
			"OR (:category = '19' AND A01.PUSHBABA = '1') " +
			"OR (:category = '20' AND A01.PUSHWEATHER = '1') " +
			"OR (:category = '50' AND A01.PUSHUTOKUBETSU = '1') " +
			"OR (:category = '51' AND A01.PUSHMOKUDEN = '1') " +
			"OR (:category = '52' AND A01.PUSHYOKUDEN = '1') " +
			"OR (:category = '53' AND A01.PUSHUMAWEIGHT = '1') " +
			"OR (:category = '54' AND A01.PUSHHARAI = '1') " +
			"OR (:category = '61' AND A01.PUSHFAVTOKUBETSU = '1') " +
			"OR (:category = '62' AND A01.PUSHFAVYOKUDEN = '1') " +
			"OR (:category = '63' AND A01.PUSHFAVHARAI = '1') " +
			"OR (:category = '64' AND A01.PUSHFAVJOC = '1') " +
			"OR (:category = '90' AND A01.PUSHTHISWEEK = '1') " +
			"OR (:category = '91' AND A01.PUSHNEWS = '1')) " +
			"AND (TRIM(:target) IS NULL OR :target NOT IN ('1') OR (:target = '1' AND A01.USERKBN = '9'))", nativeQuery = true)
	List<String> getUserIdsToPushNotification(@Param("category") String category, @Param("target") String target);

	@Query(value = "SELECT DISTINCT A01.USERID "
			+ "FROM TEST3.A01 A01 "
			+ "WHERE A01.MSYFLG = '1' AND "
			+ "(TRIM(:category) IS NULL " +
			"OR :category NOT IN ('90', '91', '10', '11', '12', '13', '19', '20', '50', '51', '52', '53', '54', '61', '63', '64') " +
			"OR (:category = '10' AND A01.PUSHTORIKESHI = '1') " +
			"OR (:category = '11' AND A01.PUSHJOGAI = '1') " +
			"OR (:category = '12' AND A01.PUSHKISYU = '1') " +
			"OR (:category = '13' AND A01.PUSHJIKOKU = '1') " +
			"OR (:category = '19' AND A01.PUSHBABA = '1') " +
			"OR (:category = '20' AND A01.PUSHWEATHER = '1') " +
			"OR (:category = '50' AND A01.PUSHUTOKUBETSU = '1') " +
			"OR (:category = '51' AND A01.PUSHMOKUDEN = '1') " +
			"OR (:category = '52' AND A01.PUSHYOKUDEN = '1') " +
			"OR (:category = '53' AND A01.PUSHUMAWEIGHT = '1') " +
			"OR (:category = '54' AND A01.PUSHHARAI = '1') " +
			"OR (:category = '61' AND A01.PUSHFAVTOKUBETSU = '1') " +
			
			"OR (:category = '63' AND A01.PUSHFAVHARAI = '1') " +
			"OR (:category = '64' AND A01.PUSHFAVJOC = '1') " +
			"OR (:category = '90' AND A01.PUSHTHISWEEK = '1') " +
			"OR (:category = '91' AND A01.PUSHNEWS = '1')) " +
			"AND (TRIM(:target) IS NULL OR :target NOT IN ('1') OR (:target = '1' AND A01.USERKBN = '9')) AND A01.USERID = :userId", nativeQuery = true)
	String checkUserIdsToPushNotification(@Param("category") String category, @Param("target") String target, @Param("userId") String userId);

	@Query(value = "SELECT DISTINCT A01.USERID "
			+ "FROM TEST3.A01 A01 "
			+ "WHERE A01.MSYFLG = '1' AND A01.USERID = :userId", nativeQuery = true)
	String checkUserIdCSV( @Param("userId") String userId);
	
	@Query(value = "SELECT DISTINCT A01.USERID "
			+ "FROM TEST3.A01 A01 "
			+ "WHERE A01.MSYFLG = '1'", nativeQuery = true)
	List<String> getAllUserActive();

	
	@Query(value = " SELECT A01.*, ROWNUM AS rn\r\n" + 
			"    FROM TEST3.A01 A01\r\n" + 
			"    WHERE A01.MSYFLG = '1'\r\n" + 
			"        AND (LOWER(A01.USERID) LIKE '%' || LOWER(:keyword) || '%' OR LOWER(A01.EMAIL) LIKE '%' || LOWER(:keyword) || '%')"
			+ " OFFSET :startIndex ROWS FETCH NEXT :itemsPerPage ROWS ONLY", nativeQuery = true) 
	List<AppUser> findByMsyFlagAndEmail(String keyword,@Param("itemsPerPage") int itemsPerPage, @Param("startIndex") int startIndex);

	@Query(value ="    SELECT A01.*, ROWNUM AS rn\r\n" + 
			"    FROM TEST3.A01 A01\r\n" + 
			"    WHERE A01.MSYFLG = '1'\r\n" + 
			"        AND LOWER(A01.USERID) LIKE '%' || LOWER(:keyword) || '%' "
			+ " OFFSET :startIndex ROWS FETCH NEXT :itemsPerPage ROWS ONLY", nativeQuery = true) 
	List<AppUser> findByMsyFlagAndUserid(String keyword ,@Param("itemsPerPage") int itemsPerPage, @Param("startIndex") int startIndex);

	@Query(value = "SELECT COUNT(*) FROM TEST3.A01 A01 WHERE A01.MSYFLG = '1' AND (LOWER(A01.USERID) LIKE '%' || LOWER(:keyword) || '%' OR LOWER(A01.EMAIL) LIKE '%' || LOWER(:keyword) || '%')", nativeQuery = true)
	Long countByMsyFlag(String keyword );
	
	@Query(value = "SELECT COUNT(*) FROM TEST3.A01 A01 WHERE A01.MSYFLG = '1' ", nativeQuery = true)
	Long countByMsyFlagAll();
	
	@Query(value = 
	        "SELECT * " +
	        "FROM TEST3.A01 A01 WHERE A01.MSYFLG = '1' ", nativeQuery = true) 
	List<AppUser> findAllByMsyflg(); 
	
	@Query(value = 
	        "SELECT * FROM TEST3.A01 A01 WHERE A01.USERID LIKE '%' || :userId || '%' FETCH FIRST 1 ROW ONLY", nativeQuery = true) 
	Optional<AppUser> findById(String userId); 

}
