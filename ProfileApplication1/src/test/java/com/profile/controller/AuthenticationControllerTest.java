package com.profile.controller;

import java.util.Set;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profile.userngmt.model.LoginRequest;
import com.profile.userngmt.service.AuthenticationServiceImpl;

@SpringBootTest
public class AuthenticationControllerTest {

	@Mock
	AuthenticationServiceImpl service;
	
	@InjectMocks
	AuthenticationController controller;
	
	@Test
	public void testAuthenticateUser() {
		LoginRequest loginRequest= new LoginRequest();
		loginRequest.setUsername("sivapodili");
		loginRequest.setPassword("password");
		Authentication authToken = new UsernamePasswordAuthenticationToken (loginRequest.getUsername(), loginRequest.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
 
 
}
