package com.DigitalBooks.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class RefundTest {
	
	Refund refund=new Refund();
	
	@Test
	public void refundIdTest() {
		refund.setRefundId(1L);
		Long refundid=refund.getRefundId();
		assertEquals(1L,refundid);
		
	}
	@Test
	public void refundIdTest_negative() {
		refund.setRefundId(1L);
		Long refundid=refund.getRefundId();
		assertNotEquals(2L,refundid);
		
	}
	
	@Test
	public void paymentIdTest() {
		refund.setPaymentId(1L);
		Long paymentidid=refund.getPaymentId();
		assertEquals(1L,paymentidid);
		
	}
	
	@Test
	public void paymentIdTest_Negative() {
		refund.setPaymentId(1L);
		Long paymentidid=refund.getPaymentId();
		assertNotEquals(2L,paymentidid);
		
	}
	
	@Test
	public void refundedAmountTest() {
		refund.setRefundedAmount(120.00);
		Double refundedAmount=refund.getRefundedAmount();
		assertEquals(120.00,refundedAmount);
		
	}
	
	@Test
	public void refundedAmountTest_Negative() {
		refund.setRefundedAmount(120.00);
		Double refundedAmount=refund.getRefundedAmount();
		assertNotEquals(110.00,refundedAmount);
		
	}
	
	@Test
	public void refundedDateTest() {
		refund.setRefundDate(new Date());
		Date refundeDate=refund.getRefundDate();
		assertEquals(new Date(),refundeDate);
		
	}
	
	@Test
	public void statusIdTest() {
		refund.setStatusId(1);
		int statusid=refund.getStatusId();
		assertEquals(1,statusid);
		
	}
	
	@Test
	public void refundStatusTest() {
		refund.setRefundStatus("OK");
		String refundStatus=refund.getRefundStatus();
		assertEquals("OK",refundStatus);
		
	}
	
	@Test
	public void bookIdTest() {
		refund.setBookId(1L);
		Long bookid=refund.getBookId();
		assertEquals(1L,bookid);
		
	}
	
	@Test
	public void readerIdTest() {
		refund.setReaderId(1L);
		Long readerid=refund.getReaderId();
		assertEquals(1L,readerid);
		
	}


}
