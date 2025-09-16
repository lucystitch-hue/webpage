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

import com.imt.login.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, String> {
    @Query(value = "SELECT * FROM TEST3.A06 A06 ORDER BY A06.UPDTIMESTAMP DESC", nativeQuery = true)
    List<Template> findAll();
    
    @Query(value = "SELECT COUNT(*) FROM TEST3.A06 A06", nativeQuery = true)
    int countAll();
    
    @Query(value = "SELECT * FROM TEST3.A06 A06 ORDER BY A06.unyonum ASC", nativeQuery = true)
    List<Template> findAllGenID();
}
