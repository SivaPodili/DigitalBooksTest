package com.profile.service;

import java.util.ArrayList;
import java.util.List;

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
public class SearchProfileService {
	
private static final Logger logger =  LogManager.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	ProfileRepository profileRepository;
	/**
	 * This method returns a list of profiles based on associateName.
	 * @param associateName 
	 * @return
	 */
	//Admin can Search Profile
	public List<Profile> findByAssociateName(String associateName) {
		Preconditions.checkArgument(associateName!=null,"Associate Name cannot be empty");
		logger.info(Constants.INSIDE_SEARCHPROFILE_SERVICE);
        return profileRepository.findByAssociateName(associateName);
    }	

}
