package com.profile.userngmt.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

import com.google.common.base.Preconditions;
import com.profile.common.Path;
import com.profile.payload.response.JwtResponse;
import com.profile.payload.response.MessageResponse;

import com.profile.security.jwt.JwtUtils;
import com.profile.userngmt.dao.AuthenticationRepository;
import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;
import com.profile.userngmt.service.AuthenticationDetailsImpl;
import com.profile.userngmt.service.AuthenticationServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Path.AUTH_PATH)
@RefreshScope


public class AuthenticationController {
	
	private static final Logger logger =  LogManager.getLogger(AuthenticationController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthenticationRepository authenticationRepository;

	

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationServiceImpl authenticationServiceImpl;
	
	@Value("${user.registered.successfully}")
	   String regMsg;
	@Value("${signin.user}")
	String signinUser;
	
	@Value("${register.user}")
	String registerUser;
	
	
	
	/**
	 * authenticateUser method is used to signin the user
	 * @param loginRequest
	 * @return
	 */
	//User Sign in
		@PostMapping(Path.SIGNIN_PATH_V1)
		public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
			Preconditions.checkArgument(loginRequest!=null,"loginRequest cannot be empty");
			logger.info(signinUser);
			ResponseEntity responseEntity=ResponseEntity.badRequest().body("Invalid Credentials");
			
			try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			AuthenticationDetailsImpl userDetails = (AuthenticationDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			responseEntity=ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
			}catch(Exception e) {
				e.printStackTrace();
			}

			return responseEntity;
		}

	/**
	 * 	registerUser method is used to register the user
	 * @param signUpRequest
	 * @return
	 */
	//User Sign up
		@PostMapping(Path.SIGNUP_PATH_V1)
		public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
			Preconditions.checkArgument(signUpRequest!=null,"SignUpRequest cannot be empty");
			logger.info(registerUser);
			
			
			ResponseEntity responseEntity;
			MessageResponse messageResponse=authenticationServiceImpl.validSignupRequest(signUpRequest);
			
			if(messageResponse.getErrorcode()==400) {
				responseEntity=ResponseEntity.badRequest().body(messageResponse);
			}else {
				// Create new user's account
				User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));
				user.setRole(signUpRequest.getRole());

				authenticationRepository.save(user);
			 responseEntity =ResponseEntity.ok(new MessageResponse<String>(regMsg,200));
				
			}
			logger.info(regMsg);

			return responseEntity;

		}		
		

}
