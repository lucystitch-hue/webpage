/*
 * This file is part of Hor    @Query(value = "SELECT * FROM TEST3.A07 A07 WHERE A07.USERID = :userId AND A07.UTOKEN = :uToken", nativeQuery = true)
    TokenManagement findByUserIdAndToken(@Param("userId") String userId, @Param("uToken") String uToken);

    @Query(value = "SELECT A07.UTOKEN FROM TEST3.A07 A07 WHERE A07.USERID = :userId ", nativeQuery = true)acing.
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

import com.imt.login.model.TokenManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenManagementRepository extends JpaRepository<TokenManagement, String> {

    @Query(value = "SELECT * FROM TEST3.A07 A07 WHERE A07.USERID = :userId AND A07.UTOKEN = :uToken", nativeQuery = true)
    TokenManagement findByuTokenAndUserId(@Param("userId") String userId, @Param("uToken") String uToken);

    @Query(value = "SELECT A07.UTOKEN FROM TEST3.A07 A07 WHERE A07.USERID = :userId ", nativeQuery = true)
    List<String> findAllByUserId(@Param("userId") String userId);
}
