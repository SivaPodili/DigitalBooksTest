package com.profile.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.common.Constants;
import com.profile.common.Path;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Path.AUTH_PATH)
public class TestController {
	@GetMapping(Path.ALL_PATH)
	public String allAccess() {
		return Constants.PUBLIC_ACCESS;
	}
	
	@GetMapping(Path.USER_PATH)
	//@PreAuthorize("hasRole('USER')")
	public String userAccess() {
		return Constants.USER_ACCESS;
	}


	@GetMapping(Path.ADMIN_PATH)
	//@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return Constants.ADMIN_ACCESS;
	}
}
