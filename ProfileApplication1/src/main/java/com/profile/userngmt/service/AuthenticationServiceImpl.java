package com.profile.userngmt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.profile.controller.AuthenticationController;
import com.profile.dao.ProfileRepository;
import com.profile.model.Profile;
import com.profile.payload.response.MessageResponse;
import com.profile.userngmt.dao.AuthenticationRepository;
import com.profile.userngmt.model.ERole;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;

@Service
@RefreshScope
public class AuthenticationServiceImpl implements UserDetailsService {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	AuthenticationRepository authenticationRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Value("${username.not.found}")
	String userNotfound;
	
	@Value("${inside.invalidsignuprequest}")
	String invalidSignup;
	
	@Value("${error.username}")
	String invalidUsername;
	
	@Value("${error.email}")
	String invalidEmail;
	
	@Value("${error.role}")
	String invalidRole;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Preconditions.checkArgument(username!=null,"Username cannot be empty");
		User user = authenticationRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(userNotfound + username));

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
		Preconditions.checkArgument(signUpRequest!=null,"SignUpRequest cannot be empty");
		List<String> errors=new ArrayList<>();
		
		logger.info(invalidSignup);
		
		if(signUpRequest.getUsername().isBlank()||authenticationRepository.existsByUsername(signUpRequest.getUsername())) {
			errors.add(invalidUsername);
			logger.debug(invalidUsername);
		}
		if(signUpRequest.getEmail().isBlank()||authenticationRepository.existsByEmail(signUpRequest.getEmail())) {
			errors.add(invalidEmail);
			logger.debug(invalidEmail);
			
		}
		
		/*
		 * In general, role is either USER or ADMIN. Others are considered as Illegal Arguments.
		 * Invalid Role error message will be added when an Illegal Argument Exception occured.
		 */
		try {
			ERole.textValueOf(signUpRequest.getRole());
		} catch (Exception e) {
			logger.debug(invalidRole);
			errors.add(invalidRole);
			
		}
		/*
		 * During the validation, multiple errors may occur.
		 * In such case, size of the error list will be greater than zero.
		 * If errors occur, errorcode will be 400 else 200
		 */
		
		int errorcode=(errors.size()>0)?400:200;
		return new MessageResponse<List<String>>(errors,errorcode);
		
	}
	
	
}
