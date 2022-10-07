package com.DigitalBooks.payload.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RefundRequestTest {
	RefundRequest refundRequest= new RefundRequest();
	
	
	@Test
	public void paymentIdTest() {
		refundRequest.setPaymentId("1");
		String paymentId=refundRequest.getPaymentId();
		assertEquals("1",paymentId);
		
	}
	
	@Test
	public void emailTest() {
		refundRequest.setEmail("siva@gmail.com");
		String email=refundRequest.getEmail();
		assertEquals("siva@gmail.com",email);
		
	}
	
	@Test
	public void bookIdTest() {
		refundRequest.setBookId("1");
		String bookId=refundRequest.getBookId();
		assertEquals("1",bookId);
		
	}
	
	@Test
	public void refundAmountTest() {
		refundRequest.setRefundAmount(222.00);
		Double refundAmount=refundRequest.getRefundAmount();
		assertEquals(222.00,refundAmount);
		
	}
	

}
