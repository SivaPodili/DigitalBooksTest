package com.DigitalBooks.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class AuthorTest {
	Author author=new Author();
	
	@Test
	public void iDtest() {
		author.setId(1L);
		Long id=author.getId();
		assertEquals(1L,id);
		
		
	}
	
	@Test
	public void userNameTest() {
		author.setUsername("siva");
		String name=author.getUsername();
		assertEquals("siva",name);
		
		
	}
	
	@Test
	public void emailTest() {
		author.setEmail("siva@gmail.com");
		String email=author.getEmail();
		assertEquals("siva@gmail.com",email);
		
		
	}
	
	@Test
	public void passwordTest() {
		author.setPassword("password");
		String pass=author.getPassword();
		assertEquals("password",pass);
		
		
	}
	
	@Test
	public void roleTest() {
		Set<Role> roles = new HashSet<>();
		author.setRoles(roles);
		Set<Role> role1 =author.getRoles();
		assertEquals(role1,roles);
		
	}
	
	@Test
	public void ConstructorTest() {
		String username="siva";
		String email="siva@gmail.com";
		String password="password";
		
		Author author1=new Author(username,email,password);
		assertEquals(username, author1.getUsername());
		assertEquals(email, author1.getEmail());
		assertEquals(password, author1.getPassword());
		
		
	}

}
