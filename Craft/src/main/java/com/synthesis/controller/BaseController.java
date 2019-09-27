package com.synthesis.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.synthesis.entity.User;
import com.synthesis.service.UserService;

@Controller
public class BaseController {
	
	
@Autowired	
UserService service;

	protected User validateUser(HttpServletRequest request) {
		final String authorization = request.getHeader("Authorization");
		String[] values = null;
		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			values = credentials.split(":", 2);
			
			User user = service.validateUser(values[0],values[1]);
			return user;
		}
		return null;
	}

}
