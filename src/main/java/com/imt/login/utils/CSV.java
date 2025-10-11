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
package com.imt.login.utils;

import com.imt.login.component.MyService;
import com.imt.login.dto.AppUserCSVDTO;
import com.imt.login.dto.TemporaryUserDTO;
import com.imt.login.model.AppUser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;
public class CSV {

    /**
     * This is a method that reads data from a CSV file and returns a map of AppUserDTO objects.
     * The method takes a filename as input and uses a BufferedReader to read the file line by line.
     * It then splits each line into an array of strings using a comma as the delimiter.
     * The first element of the array is used as the key for the map, and the second element is used to create a new AppUserDTO object.
     * The key and user object are then added to the map.
     * The method returns the map of AppUserDTO objects.
     */

    public static List<String> readCSVContent() {
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

        try {
            List<Path> csvFiles = Files.list(tempDir)
                    .filter(path -> path.toString().toLowerCase().contains("listusercsvinput"))
                    .collect(Collectors.toList());

            if (csvFiles.isEmpty()) {
                System.out.println("No matching CSV files found.");
                return new ArrayList<>();
            }

            List<String> listUserId = new ArrayList<>();
            List<String> contentFile = Files.readAllLines(csvFiles.get(0));

            // Skip the header line (assuming it is always present)
            System.out.printf("\n-----------------\n");
            System.out.printf("\nUser from CSV:\n");
            
            for (String line : contentFile) {
                String[] values = line.split("\n");
                String userId = values[0].substring(0, 40).replace("\"", "").trim();
                listUserId.add(userId.replace("\"", ""));
                System.out.println("User ID: " + userId);
            }
            System.out.printf("\n-----------------\n");

            return listUserId;
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static File exportLogTemporaryUser(List<TemporaryUserDTO> list, String key) {
        FileWriter csvWriter = null;
        try {
        	Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
            File tempFile = new File(tempDir.toString()+"/"+key + ".csv");
            System.out.printf("File path: %s%n", tempFile.getPath());

            csvWriter = new FileWriter(tempFile);
            for (TemporaryUserDTO obj : list) {
                csvWriter.append(obj.getUserId()).append(",");
                csvWriter.append(obj.getEmail()).append(",");
                csvWriter.append(obj.getCreated()).append(",");
                csvWriter.append(String.valueOf(obj.isSuccessDelete())).append(",");
                csvWriter.append("\n");
            }
            csvWriter.flush();
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

	public static File downLogTemporaryUser(String key) {
		Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
        String pathFile =tempDir.toString();
		File folder2 = new File(pathFile);
		File[] files = folder2.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().equals(key + ".csv")) {
					return file;
				}
			}
		}
		return null;
	}
	
	public static File fileCsvListUser(MultipartFile file) {
		try {
			 byte[] fileBytes = file.getBytes();
			 Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "listusercsvinput.csv");
			 Files.write(tempFilePath, fileBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	return null;
    }
}
