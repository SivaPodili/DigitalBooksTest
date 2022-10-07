package com.DigitalBooks.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

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
		Set<String> role=new HashSet<String>();
		
		
		signupRequest.setRole(role);
		
		Set<String> role1 =signupRequest.getRole();
		assertEquals(role1,role);
		
	}

}
