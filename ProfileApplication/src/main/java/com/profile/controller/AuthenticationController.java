package com.profile.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.profile.common.Constants;
import com.profile.common.Path;
import com.profile.model.Profile;
import com.profile.payload.response.JwtResponse;
import com.profile.payload.response.MessageResponse;

import com.profile.security.jwt.JwtUtils;
import com.profile.userngmt.dao.UserRepository;
import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;
import com.profile.userngmt.service.AuthenticationDetailsImpl;
import com.profile.userngmt.service.AuthenticationDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Path.AUTH_PATH)
public class AuthenticationController {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationDetailsServiceImpl authenticationDetailsServiceImpl;
	
	/**
	 * authenticateUser method is used to signin the user
	 * @param loginRequest
	 * @return
	 */
	//User Sign in
		@PostMapping(Path.SIGNIN_PATH_V1)
		public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
			
			logger.info(Constants.SIGNIN_USER);

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			AuthenticationDetailsImpl userDetails = (AuthenticationDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		}

	/**
	 * 	registerUser method is used to register the user
	 * @param signUpRequest
	 * @return
	 */
	//User Sign up
		@PostMapping(Path.SIGNUP_PATH_V1)
		public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
			
			logger.info(Constants.REGISTER_USER);
			
			
			ResponseEntity responseEntity;
			MessageResponse messageResponse=authenticationDetailsServiceImpl.validSignupRequest(signUpRequest);
			
			if(messageResponse.getErrorcode()==Constants.BADREQUEST) {
				responseEntity=ResponseEntity.badRequest().body(messageResponse);
			}else {
				// Create new user's account
				User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));
				user.setRole(signUpRequest.getRole());

				userRepository.save(user);
			 responseEntity =ResponseEntity.ok(new MessageResponse<String>(Constants.USER_REGISTERED_SUCCESSFULLY,Constants.SUCCESS));
				
			}
			logger.info(Constants.USER_REGISTERED_SUCCESSFULLY);

			return responseEntity;

		}		
		

}
