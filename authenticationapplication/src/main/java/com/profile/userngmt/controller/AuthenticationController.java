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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.profile.userngmt.common.Path;
import com.profile.userngmt.dao.AuthenticationRepository;
import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;
import com.profile.userngmt.payload.response.JwtResponse;
import com.profile.userngmt.payload.response.MessageResponse;
import com.profile.userngmt.security.jwt.JwtUtils;
import com.profile.userngmt.service.AuthenticationDetailsImpl;
import com.profile.userngmt.service.AuthenticationServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	
	@ApiOperation(value="Signin",notes="Request User to Signin", nickname="signin")
	
	@ApiResponses(value = {
			
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	         @ApiResponse(code = 400, message = "Invalid Credentials"),
	        @ApiResponse(code = 200, message = "User Successfully Signed in"
	           ) })
	
		@PostMapping(Path.SIGNIN_PATH_V1)
		public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
			Preconditions.checkArgument(loginRequest!=null,"loginRequest cannot be empty");
			logger.info(signinUser);
			ResponseEntity<?> responseEntity=ResponseEntity.badRequest().body("Invalid Credentials");

			try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			AuthenticationDetailsImpl userDetails = (AuthenticationDetailsImpl) authentication.getPrincipal();
			responseEntity=ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
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
	
	@ApiOperation(value="registerUser",notes="Request User to register", nickname="registerUser")
	
	@ApiResponses(value = {
			
	        @ApiResponse(code = 500, message = "Internal Server Error"),
	         @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 200, message = "User Successfully Registered"
	           ) })
	
		@PostMapping(Path.SIGNUP_PATH_V1)
		public ResponseEntity registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
			Preconditions.checkArgument(signUpRequest!=null,"SignUpRequest cannot be empty");
			logger.info(registerUser);


			ResponseEntity<?> responseEntity;
			MessageResponse<?> messageResponse=authenticationServiceImpl.validSignupRequest(signUpRequest);

			if(messageResponse.getErrorcode()==400) {
				responseEntity=ResponseEntity.badRequest().body(messageResponse);
			}else {
				// Create new user's account
				User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));

				authenticationRepository.save(user);
			 responseEntity =ResponseEntity.ok(new MessageResponse<>(regMsg,200));

			}
			logger.info(regMsg);

			return responseEntity;

		}


}
