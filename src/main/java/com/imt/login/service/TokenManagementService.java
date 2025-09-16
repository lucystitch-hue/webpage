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
package com.imt.login.service;

import com.imt.login.exception.MessageHandle;
import com.imt.login.model.TokenManagement;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TokenManagementService {
    List<TokenManagement> listAll();

    TokenManagement findById(Long id);

    ResponseEntity<MessageHandle> create(String userId, String tknNum, String uToken);

    ResponseEntity delete(Map<String,String> data);

}
