package com.DigitalBooks.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class PaymentTest {
	
Payment payment=new Payment();
    
    @Test
    public void paymentIdtest() {
        payment.setPaymentId(1L);
        Long id=payment.getPaymentId();
        assertEquals(1L,id);
    }
    @Test
    public void readerIdtest() {
        payment.setReaderId(1L);
        Long id=payment.getReaderId();
        assertEquals(1L,id);
    }
    @Test
    public void bookidtest()
    {
        payment.setBookiD(3);
        int id=payment.getBookiD();
        assertEquals(3,id);
    }
    @Test
    public void pricetest()
    {
        payment.setPrice(150);
        int price=payment.getPrice();
        assertEquals(150,price);
    }
    @Test
    public void emailtest()
    {
        payment.setEmail("siva@gmail.com");
        String email=payment.getEmail();
        assertEquals("siva@gmail.com",email);
    }
    
    @Test
    public void paymentDateTest()
    {
        payment.setPaymentDate(new Date());
      Date date=  payment.getPaymentDate();
        
        assertEquals(new Date(),date);
    }
    
}


