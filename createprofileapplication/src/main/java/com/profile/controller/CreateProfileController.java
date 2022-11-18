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
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.profile.common.Path;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.service.CreateProfileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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

	@ApiOperation(value="createProfile",notes="create profile method is used to add profiles to the database", nickname="createProfile")
	
	@ApiResponses(value = {
			
	        @ApiResponse(code = 500, message = "Unable to create profile"),
	         @ApiResponse(code = 400, message = "Associate Id must starts with CTS"),
	        @ApiResponse(code = 200, message = "User Successfully Created the profile"
	           ) })
	@PostMapping(Path.CREATEPROFILE_PATH_V1)
	public ResponseEntity createProfile(@RequestBody @Valid Profile profile) {
		Preconditions.checkArgument(profile!=null,"Profile cannot be empty");
		ResponseEntity<?> responseEntity;
		logger.info(insideCPController);
		MessageResponse<?> messageResponse;

		messageResponse=createProfileService.createProfileService(profile);
		
		if(!profile.getAssociateId().startsWith("CTS")) {
			messageResponse=new MessageResponse<>("Associate Id must starts with CTS",400);
			
		}
		if(messageResponse!=null && messageResponse.getErrorcode()==200) {
			responseEntity=ResponseEntity.ok(messageResponse);
		}else {
			responseEntity=ResponseEntity.unprocessableEntity().body(messageResponse);
		}
		return responseEntity;
	}

}
