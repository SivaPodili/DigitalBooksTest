package com.profile.userngmt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SignupRequestTest {

SignupRequest signupRequest= new SignupRequest();
	
	@Test
	public void UsernameTest() {
		signupRequest.setUsername("siva");
		String username=signupRequest.getUsername();
		assertEquals("siva", username);
		
	}
	
	@Test
	public void emailTest() {
		signupRequest.setEmail("siva@gmail.com");
		String email=signupRequest.getEmail();
		assertEquals("siva@gmail.com", email);
		
	}
	
	@Test
	public void passwordTest() {
		signupRequest.setPassword("password");
		String password=signupRequest.getPassword();
		assertEquals("password", password);
		
	}
	
	@Test
	public void roleTest() {
		
		signupRequest.setRole("USER");
		
		String role =signupRequest.getRole();
		assertEquals("USER",role);
		
	}


}
