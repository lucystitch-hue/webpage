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
package com.imt.login.controller;

import com.imt.login.service.impl.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Controller
public class SendEmailController {

	@Autowired
	SendEmailService sendEmailService;

	@GetMapping("/faq/faq_mail.html")
	public String newInquiry(Model model, @RequestParam("email") Optional<String> email,
							 @RequestParam("ver") Optional<String> version, @RequestParam("send") Optional<Boolean> send,
							 @RequestParam(name = "category", defaultValue = "0") String category) {

		model.addAttribute("email", email.orElse(""));
		model.addAttribute("category", category);
		model.addAttribute("device", "");
		model.addAttribute("system", "");
		model.addAttribute("steelSueName", "");
		model.addAttribute("africaVersion", version.orElse(""));
		model.addAttribute("content", "");
		return "faq_mail";
	}

	@PostMapping("/faq/faq_mail.html")
	public String submitInquiry(HttpServletRequest request, Model model, @RequestParam("email") String email,
								@RequestParam("category") String category, @RequestParam("device") String device,
								@RequestParam("system") String system, @RequestParam("steelSueName") Optional<String> steelSueName,
								@RequestParam("africaVersion") Optional<String> africaVersion, @RequestParam("content") String content) {
		String userAgent = request.getHeader("User-Agent");
		if(userAgent.equals("TESTAPP")){
			model.addAttribute("userAgent", userAgent);
		}

		model.addAttribute("email", email);
		model.addAttribute("africaVersion", africaVersion.orElse(""));
		Map<String, Object> resultMail = sendEmailService.sendEmail(email, category, device, system,
				steelSueName.orElse(""), africaVersion.orElse(""), content);
		Boolean success = (Boolean) resultMail.get("success");
		String message = (String) resultMail.get("message");
		model.addAttribute("message", message);
		model.addAttribute("success", success);

		if (!success) {
			model.addAttribute("category", category);
			model.addAttribute("device", device);
			model.addAttribute("system", system);
			model.addAttribute("steelSueName", steelSueName.orElse(""));
			model.addAttribute("content", content);
		}
		return "faq_mail_success";
	}
}
