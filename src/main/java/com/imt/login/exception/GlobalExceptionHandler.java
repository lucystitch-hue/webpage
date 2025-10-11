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
//package com.imt.login.exception;
//
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler  {
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public TokenManagementException handleAllException(Exception ex, WebRequest request) {
//
//        return new TokenManagementException("false", ex.getLocalizedMessage());
//    }
//
//    /**
//     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
//     */
//    @ExceptionHandler(IndexOutOfBoundsException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public TokenManagementException TodoException(Exception ex, WebRequest request) {
//        return new TokenManagementException("false", "Object does not exist");
//    }
//}
