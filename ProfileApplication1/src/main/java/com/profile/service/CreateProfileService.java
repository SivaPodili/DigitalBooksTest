package com.profile.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.userngmt.service.AuthenticationServiceImpl;

@Service
@RefreshScope
public class CreateProfileService {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Value("${inside.createprofile.service}")
	String insideCPService;
	
	@Value("${profile.created}")
	String profileCreated;
	
	@Value("${unable.create.profile}")
	String unableCreateProfile;
	
	
	
	/**
	 * This method creates profile.
	 * 
	 * @param Profile is an object that contains vital information like username, email etc..
	 * @return  
	 */
	//User can create profile
	public MessageResponse<String> createProfileService(Profile profile) {
		Preconditions.checkArgument(profile!=null,"Profile cannot be empty");
		logger.info(insideCPService);
		MessageResponse<String> response=new MessageResponse<>();
		try {
			profileRepository.save(profile);
			response.setErrorcode(200);
			response.setMessage(profileCreated);
		}catch(Exception e){
			logger.error("error:"+e.getMessage());
			e.printStackTrace();
			response.setErrorcode(500);
			response.setMessage(unableCreateProfile);
		}
		return response;

	}

}
