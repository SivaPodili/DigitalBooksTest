package com.DigitalBooks.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.DigitalBooks.models.Author;

public class JwtResponseTest {
	JwtResponse jwtResponse= new JwtResponse();
	
	@Test
	public void TokenTest() {
		jwtResponse.setAccessToken("Token");
		String token=jwtResponse.getAccessToken();
		assertEquals("Token", token);
		
	}
	
	@Test
	public void BearerTypeTest() {
		jwtResponse.setTokenType("Bearer");
		String bearertype=jwtResponse.getTokenType();
		assertEquals("Bearer", bearertype);
		
	}
	
	@Test
	public void idTest() {
		jwtResponse.setId(1L);
		Long id=jwtResponse.getId();
		assertEquals(1L, id);
		
	}
	
	
	@Test
	public void emailTest() {
		jwtResponse.setEmail("siva@gmail.com");
		String email=jwtResponse.getEmail();
		assertEquals("siva@gmail.com", email);
		
	}
	
	@Test
	public void UsernameTest() {
		jwtResponse.setUsername("siva");
		String username=jwtResponse.getUsername();
		assertEquals("siva", username);
		
	}
	
	@Test
	public void ConstructorTest() {
		 String token="token";
		 Long id=1L;
		 String username="siva";
		 String email="siva@gmail.com";
		 List<String> roles=new ArrayList<>();
		
		JwtResponse jwtResponse= new JwtResponse(token,id,username,email,roles);
		assertEquals(token, jwtResponse.getAccessToken());
		assertEquals(id, jwtResponse.getId());
		assertEquals(username, jwtResponse.getUsername());
		assertEquals(email, jwtResponse.getEmail());
		assertEquals(roles, jwtResponse.getRoles());
		
		
	}



}
