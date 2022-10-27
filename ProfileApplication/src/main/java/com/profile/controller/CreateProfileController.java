package com.profile.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.common.Constants;
import com.profile.common.Path;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.service.CreateProfileService;
import com.profile.userngmt.service.AuthenticationDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Path.AUTH_PATH)

public class CreateProfileController {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationController.class);
	
	@Autowired
	CreateProfileService createProfileService;
	
	/**
	 * createProfile method is used to create a profile.
	 * @param profile 
	 * @return
	 * constant string is returned.
	 */
	
	@PostMapping(Path.CREATEPROFILE_PATH_V1)
	public ResponseEntity<?> createProfile(@RequestBody @Valid Profile profile) {
		ResponseEntity responseEntity;
		logger.info(Constants.INSIDE_CREATEPROFILE_CONTROLLER);
		MessageResponse messageResponse=createProfileService.createProfileService(profile);
		if(messageResponse.getErrorcode()==Constants.SUCCESS) {
			responseEntity=ResponseEntity.ok(messageResponse);
		}else {
			responseEntity=ResponseEntity.unprocessableEntity().body(messageResponse);
		}
		return responseEntity;
	}

}
