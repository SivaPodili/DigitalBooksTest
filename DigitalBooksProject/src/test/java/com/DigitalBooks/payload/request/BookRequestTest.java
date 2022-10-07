package com.DigitalBooks.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BookRequestTest {
	BookRequest bookRequest=new BookRequest();
	
	@Test
	public void UsernameTest() {
		bookRequest.setUsername("siva");
		String username=bookRequest.getUsername();
		assertEquals("siva", username);
		
	}
	@Test
	public void emailTest() {
		bookRequest.setEmail("siva@gmail.com");
		String email=bookRequest.getEmail();
		assertEquals("siva@gmail.com", email);
		
	}
	
	@Test
	public void bookIdTest() {
		bookRequest.setBookid("1");
		String bookid=bookRequest.getBookid();
		assertEquals("1", bookid);
		
	}

}
