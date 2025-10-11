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


	@Query(value = "", nativeQuery = true)
	List<String> getUserIdsToPushNotification(@Param("category") String category, @Param("target") String target);

	@Query(value = "", nativeQuery = true)
	String checkUserIdsToPushNotification(@Param("category") String category, @Param("target") String target, @Param("userId") String userId);

	@Query(value = "", nativeQuery = true)
	String checkUserIdCSV( @Param("userId") String userId);
	
	@Query(value = "", nativeQuery = true)
	List<String> getAllUserActive();

	
	@Query(value = " ", nativeQuery = true) 
	List<AppUser> findByMsyFlagAndEmail(String keyword,@Param("itemsPerPage") int itemsPerPage, @Param("startIndex") int startIndex);

	@Query(value =" ", nativeQuery = true) 
	List<AppUser> findByMsyFlagAndUserid(String keyword ,@Param("itemsPerPage") int itemsPerPage, @Param("startIndex") int startIndex);

	@Query(value = "", nativeQuery = true)
	Long countByMsyFlag(String keyword );
	
	@Query(value = " ", nativeQuery = true)
	Long countByMsyFlagAll();
	
	@Query(value = 
	        " ", nativeQuery = true) 
	List<AppUser> findAllByMsyflg(); 
	
	@Query(value = 
	        "", nativeQuery = true) 
	Optional<AppUser> findById(String userId); 

}
