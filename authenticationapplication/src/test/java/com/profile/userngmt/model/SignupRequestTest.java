package com.profile.userngmt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SignupRequestTest {

SignupRequest signupRequest= new SignupRequest();
	
	@Test
	void UsernameTest() {
		signupRequest.setUsername("siva");
		String username=signupRequest.getUsername();
		assertEquals("siva", username);
		
	}
	
	@Test
	void emailTest() {
		signupRequest.setEmail("siva@gmail.com");
		String email=signupRequest.getEmail();
		assertEquals("siva@gmail.com", email);
		
	}
	
	@Test
	void passwordTest() {
		signupRequest.setPassword("password");
		String password=signupRequest.getPassword();
		assertEquals("password", password);
		
	}
	
	


}
