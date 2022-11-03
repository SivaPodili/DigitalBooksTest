package com.profile.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.profile.common.Path;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.service.CreateProfileService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Path.AUTH_PATH)
@RefreshScope

public class CreateProfileController {
	
	private static final Logger logger =  LogManager.getLogger(CreateProfileController.class);
	
	@Autowired
	CreateProfileService createProfileService;
	
	@Value("${inside.createprofile.controller}")
	String insideCPController;
	
	/**
	 * createProfile method is used to create a profile.
	 * @param profile 
	 * @return
	 * constant string is returned.
	 */
	
	@PostMapping(Path.CREATEPROFILE_PATH_V1)
	public ResponseEntity<?> createProfile(@RequestBody @Valid Profile profile) {
		Preconditions.checkArgument(profile!=null,"Profile cannot be empty");
		ResponseEntity responseEntity;
		logger.info(insideCPController);
		MessageResponse messageResponse;
		
		messageResponse=createProfileService.createProfileService(profile);
		if(messageResponse!=null && messageResponse.getErrorcode()==200) {
			responseEntity=ResponseEntity.ok(messageResponse);
		}else {
			responseEntity=ResponseEntity.unprocessableEntity().body(messageResponse);
		}
		return responseEntity;
	}

}
