package com.profile.userngmt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserTest {

User author=new User();
@Test	
void idTest() {
	author.setId(1L);
	Long id=author.getId();
	assertEquals(1L, id);
}
	
	@Test
void userNameTest() {
		author.setUsername("siva");
		String name=author.getUsername();
		assertEquals("siva",name);
		
		
	}
	
	@Test
	void emailTest() {
		author.setEmail("siva@gmail.com");
		String email=author.getEmail();
		assertEquals("siva@gmail.com",email);
		
		
	}
	
	@Test
	void passwordTest() {
		author.setPassword("password");
		String pass=author.getPassword();
		assertEquals("password",pass);
		
		
	}
	
	
	@Test
	void ConstructorTest() {
		String username="siva";
		String email="siva@gmail.com";
		String password="password";
		
		User author1=new User(username,email,password);
		assertEquals(username, author1.getUsername());
		assertEquals(email, author1.getEmail());
		assertEquals(password, author1.getPassword());
		
		
	}

}
