package com.profile.userngmt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.profile.common.Constants;
import com.profile.controller.AuthenticationController;
import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.userngmt.dao.UserRepository;
import com.profile.userngmt.model.ERole;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;

@Service
public class AuthenticationDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationDetailsServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND + username));

		return AuthenticationDetailsImpl.build(user);
	}
	
	/**
	 * ValidSignupRequest method validates the input request.
	 * It validates important @param signUpRequest input fields like Username, email and Roles
	 * 
	 * @return 
	 * When validation is success, 200 is returned else 
	 * Errorcode 400 and errors will be return
	 * 
	 */
	
	public MessageResponse validSignupRequest(SignupRequest signUpRequest) {
		List<String> errors=new ArrayList<>();
		
		logger.info(Constants.INSIDE_INVALIDSIGNUPREQUEST);
		
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
			errors.add(Constants.ERROR_USERNAME);
			logger.debug(Constants.ERROR_USERNAME);
		}
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			errors.add(Constants.ERROR_EMAIL);
			logger.debug(Constants.ERROR_EMAIL);
			
		}
		
		/*
		 * In general, role is either USER or ADMIN. Others are considered as Illegal Arguments.
		 * Invalid Role error message will be added when an Illegal Argument Exception occured.
		 */
		try {
			ERole.textValueOf(signUpRequest.getRole());
		} catch (Exception e) {
			logger.debug(Constants.ERROR_ROLE);
			errors.add(Constants.ERROR_ROLE);
			
		}
		/*
		 * During the validation, multiple errors may occur.
		 * In such case, size of the error list will be greater than zero.
		 * If errors occur, errorcode will be 400 else 200
		 */
		
		int errorcode=(errors.size()>0)?Constants.BADREQUEST:Constants.SUCCESS;
		return new MessageResponse<List<String>>(errors,errorcode);
		
	}
	/**
	 * This method creates profile.
	 * 
	 * @param Profile is an object that contains vital information like username, email etc..
	 * @return  
	 */
	//User/Admin can create profile
//	public MessageResponse<String> createProfileService(Profile profile) {
//		logger.info(Constants.INSIDE_CREATEPROFILE_SERVICE);
//		MessageResponse<String> response=new MessageResponse<>();
//		try {
//			profileRepository.save(profile);
//			response.setErrorcode(Constants.SUCCESS);
//			response.setMessage(Constants.PROFILE_CREATED);
//		}catch(Exception e){
//			logger.error("error:"+e.getMessage());
//			e.printStackTrace();
//			response.setErrorcode(Constants.SERVER_ERROR);
//			response.setMessage("Unable to create a profile");
//		}
//		return response;
//
//	}
	
	/**
	 * This method returns a list of profiles based on associateName.
	 * @param associateName 
	 * @return
	 */
	//Admin can Search Profile
//	public List<Profile> findByName(String associateName) {
//		logger.info(Constants.INSIDE_SEARCHPROFILE_SERVICE);
//        return profileRepository.findByAssociateName(associateName);
//    }
	
}
