package com.profile.userngmt.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.profile.common.Constants;
import com.profile.controller.AuthenticationController;
import com.profile.payload.response.JwtResponse;
import com.profile.payload.response.MessageResponse;
import com.profile.security.jwt.JwtUtils;
import com.profile.userngmt.dao.AuthenticationRepository;
import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.model.SignupRequest;
import com.profile.userngmt.model.User;
import com.profile.userngmt.service.AuthenticationDetailsImpl;

@SpringBootTest( 
		  properties = {"spring.cloud.config.enabled=false"}
		)
public class AuthenticationFlowTest {
	
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
	public void entryNullCheckTest() {
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
	public void entryPositiveTest() {
		LoginRequest loginRequest=new LoginRequest();
		SignupRequest signupRequest=new SignupRequest();
		User user=new User();
		
		signupRequest.setUsername("rammohan");
		signupRequest.setPassword("Abcd1245@");
		signupRequest.setEmail("mohan@gmail.com");
		signupRequest.setRole("USER");
		
		ResponseEntity responseEntity=authenticationController.registerUser(signupRequest);
		assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
		
		loginRequest.setUsername("sivapodili");
		loginRequest.setPassword("password");
		ResponseEntity responseEntity1=authenticationController.authenticateUser(loginRequest);
		assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);		
		
	}
	
	@Test
	public void entryNegativeTest() {
		
		
		signupRequest.setUsername("");
		signupRequest.setPassword("");
		signupRequest.setEmail("");
		signupRequest.setRole("");
		
		ResponseEntity responseEntity=authenticationController.registerUser(signupRequest);
		 assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
		
		loginRequest.setUsername("");
		loginRequest.setPassword("");
		
		ResponseEntity responseEntity1=authenticationController.authenticateUser(loginRequest);
			 assertEquals(responseEntity1.getStatusCode(),HttpStatus.BAD_REQUEST);
	}
	
	

}
