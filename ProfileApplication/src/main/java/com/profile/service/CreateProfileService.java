package com.profile.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.profile.common.Constants;
import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.userngmt.service.AuthenticationServiceImpl;

@Service
public class CreateProfileService {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	ProfileRepository profileRepository;
	
	/**
	 * This method creates profile.
	 * 
	 * @param Profile is an object that contains vital information like username, email etc..
	 * @return  
	 */
	//User can create profile
	public MessageResponse<String> createProfileService(Profile profile) {
		Preconditions.checkArgument(profile!=null,"Profile cannot be empty");
		logger.info(Constants.INSIDE_CREATEPROFILE_SERVICE);
		MessageResponse<String> response=new MessageResponse<>();
		try {
			profileRepository.save(profile);
			response.setErrorcode(Constants.SUCCESS);
			response.setMessage(Constants.PROFILE_CREATED);
		}catch(Exception e){
			logger.error("error:"+e.getMessage());
			e.printStackTrace();
			response.setErrorcode(Constants.SERVER_ERROR);
			response.setMessage(Constants.UNABLE_CTREATE_PROFILE);
		}
		return response;

	}

}
