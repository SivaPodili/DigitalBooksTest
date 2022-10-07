package com.DigitalBooks.payload.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.DigitalBooks.models.Author;

class MessageResponseTest {
	MessageResponse message=new MessageResponse();

	@Test
	public void messageTest() {
		message.setMessage("Successfully");
		String message1=message.getMessage();
		assertEquals("Successfully",message1);
	}

	@Test
	public void ConstructorTest() {
		String msg="successfully";
		
		MessageResponse message=new MessageResponse(msg);
		assertEquals(msg, message.getMessage());
		
		
		
	}
}
