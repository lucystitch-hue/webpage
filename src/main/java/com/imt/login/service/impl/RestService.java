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

import com.google.gson.Gson;
import com.imt.login.constan.Constans;
import com.imt.login.exception.MessageCheck;
import com.imt.login.exception.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;



public class RestService {
    public static final String GRANT_TYPE = "grant_type=";

    public static final String CLIENT_CREDENTIALS = "client_credentials";

    public static final String URN_OPC_IDM_MYSCOPES = "urn:opc:idm:__myscopes__";

    public static final String SCOPE = "&scope=";

    public static final String ACCESS_TOKEN = "access_token";

    private static Logger logger = LoggerFactory.getLogger(RestService.class);

    public static String getRest(String url, Map<String, String> headers) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        try {
            con.setRequestMethod("GET");

            // set headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int responseCode = con.getResponseCode();

            BufferedReader in;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return response.toString();
            } else {
                throw new Exception("HTTP request failed with response code: " + responseCode +
                        ", response: " + response.toString());
            }
        } finally {
            con.disconnect();
        }
    }

    public static MessageCheck getTokenPostRest(String url, Map<String, String> header) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // set the request method
        con.setRequestMethod("POST");
        // set heeader
        header.forEach((key, value) -> {
            con.setRequestProperty(key, value);
        });

        // set the request body
        String body = GRANT_TYPE + CLIENT_CREDENTIALS + SCOPE + URN_OPC_IDM_MYSCOPES;
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        // get the response
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = in.lines().reduce("", (a, b) -> a + b);

            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(response, Map.class);
            String accessToken = (String) map.get(ACCESS_TOKEN);
            in.close();
            return new MessageCheck(true, con.getResponseMessage(), accessToken);
        } else {
            return new MessageCheck(false, con.getResponseMessage(), null);
        }
    }

    public static MessageData dropUserDeleteRest(String urlDelete, Object token) throws IOException {
        URL obj = new URL(urlDelete);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");

        con.setRequestProperty(Constans.AUTHORIZATION, Constans.BEARER + token);
        con.setRequestProperty(Constans.CONTENT_TYPE, Constans.APPLICATION_JSON);
        con.setRequestProperty(Constans.ACCEPT_LANGGUAGE ,Constans.JA);
        int responseCode = con.getResponseCode();
        MessageData mess = new MessageData();
//		System.out.println(Constans.RESPONSE_CODE + responseCode);
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            mess.setSuccess(Constans.TRUE);
            mess.setMessage(Constans.USER_DELETED_SUCCESSFULLY);
            return mess;
        }
        if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            mess.setSuccess(Constans.FALSE);
            mess.setMessage(
                    Constans.USER_IS_CURRENTLY_REFERENCED);
            return mess;
        }
        mess.setSuccess(Constans.FALSE);
        mess.setMessage(con.getResponseMessage() + Constans.PLEASE_LOG_OUT);
        return mess;
    }
}
