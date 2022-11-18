package com.profile.userngmt.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.profile.userngmt.dao.AuthenticationRepository;
import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.security.jwt.JwtUtils;

@SpringBootTest( 
		  properties = {"spring.cloud.config.enabled=false"}
		)
class AuthenticationFlowTest {
	
	@Autowired
	AuthenticationController authenticationController;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authenticationManager;
	
	@MockBean
	AuthenticationRepository authenticationRepository;
	
	@Autowired
	PasswordEncoder encoder;

	LoginRequest loginRequest=new LoginRequest();
	SignupRequest signupRequest=new SignupRequest();
	

	@Test
	void entryNullCheckTest() {
		boolean exceptionOccured=false;
		try {
		authenticationController.registerUser(null);
		}catch(Exception e) {
			exceptionOccured=true;
		}
		assertTrue(exceptionOccured);
		
		try {
		authenticationController.authenticateUser(null);
		}catch(Exception e) {
			exceptionOccured=true;
		}
		assertTrue(exceptionOccured);
		
	}
	
	@Test
	void entryPositiveTest() {
		LoginRequest loginRequest=new LoginRequest();
		SignupRequest signupRequest=new SignupRequest();
		signupRequest.setUsername("rammohan");
		signupRequest.setPassword("Abcd1245@");
		signupRequest.setEmail("mohan@gmail.com");
		
		
		ResponseEntity<?> responseEntity=authenticationController.registerUser(signupRequest);
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		
		loginRequest.setUsername("sivapodili");
		loginRequest.setPassword("password");
		ResponseEntity<?> responseEntity1=authenticationController.authenticateUser(loginRequest);
		assertEquals(HttpStatus.OK,responseEntity1.getStatusCode());	
		
	}
	
	@Test
	void entryNegativeTest() {
		
		
		signupRequest.setUsername("");
		signupRequest.setPassword("");
		signupRequest.setEmail("");
		
		ResponseEntity<?> responseEntity=authenticationController.registerUser(signupRequest);
		 assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
		
		loginRequest.setUsername("");
		loginRequest.setPassword("");
		
		ResponseEntity<?> responseEntity1=authenticationController.authenticateUser(loginRequest);
			 assertEquals(HttpStatus.BAD_REQUEST, responseEntity1.getStatusCode());
	}
	
	

}
