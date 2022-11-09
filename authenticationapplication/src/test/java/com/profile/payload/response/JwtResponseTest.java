package com.profile.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.profile.userngmt.payload.response.JwtResponse;

class JwtResponseTest {

	JwtResponse jwtResponse=new JwtResponse();
	@Test
	public void TokenTest() {
		jwtResponse.setAccessToken("1234abcd");
		String accesstoken=jwtResponse.getAccessToken();
		assertEquals("1234abcd",accesstoken);	
	}
	
	@Test
	public void idTest() {
		jwtResponse.setId(1L);
		Long id=jwtResponse.getId();
		assertEquals(1L,id);	
	}
	
	@Test
	public void usernameTest() {
		jwtResponse.setUsername("sivapodili");
		String username=jwtResponse.getUsername();
		assertEquals("sivapodili",username);	
	}
	
	@Test
	public void emailTest() {
		jwtResponse.setEmail("siva@gmail.com");
		String email=jwtResponse.getEmail();
		assertEquals("siva@gmail.com",email);	
	}
	
	@Test
	public void tokenTypeTest() {
		jwtResponse.setTokenType("BEARER");
		String tokentype=jwtResponse.getTokenType();
		assertEquals("BEARER",tokentype);	
	}
	
	@Test
	public void ConstructorTest() {
		String accessToken="abcd1234@";
		Long id=1L;
		String username="sivapodili";
		String email="siva@gmail.com";
		List<String> roles=new ArrayList<>();
		JwtResponse jwtResponse=new JwtResponse(accessToken, id, username, email, roles);
		assertEquals(accessToken, jwtResponse.getAccessToken());
		assertEquals(id, jwtResponse.getId());
		assertEquals(username, jwtResponse.getUsername());
		assertEquals(email, jwtResponse.getEmail());
		assertEquals(roles, jwtResponse.getRoles());
		
		
		
	}

}
