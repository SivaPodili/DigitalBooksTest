package com.DigitalBooks.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BookTest {
	Book book =new Book();
	
	@Test
	public void bookIdTest() {
		book.setBookid(1);
		int id=book.getBookId();
		assertEquals(1,id);
		
	}
	
	@Test
	public void authorTest() {
		book.setAuthor("siva");
		String author=book.getAuthor();
		assertEquals("siva",author);
		
	}
	
	
	@Test
	public void titleTest() {
		book.setTitle("Earth is Awesome");
		String title=book.getTitle();
		assertEquals("Earth is Awesome",title);
		
	}
	
	@Test
	public void catagoryTest() {
		book.setCategory("General");
		String catagory=book.getCategory();
		assertEquals("General",catagory);
		
	}
	
	@Test
	public void imageTest() {
		book.setImage("earth.jpg");
		String image=book.getImage();
		assertEquals("earth.jpg",image);
		
	}
	
	@Test
	public void priceTest() {
		book.setPrice(555);
		int price =book.getPrice();
		assertEquals(555,price);
		
	}
	
	@Test
	public void publisherTest() {
		book.setPublisher("podili");
		String publisher=book.getPublisher();
		assertEquals("podili",publisher);
		
	}
	
	@Test
	public void activeTest() {
		book.setActive("yes");
		String active=book.getActive();
		assertEquals("yes",active);
		
	}
	
	@Test
	public void contentTest() {
		book.setContent("Awesome");
		String content=book.getContent();
		assertEquals("Awesome",content);
		
	}
	
	@Test
	public void publisherDateTest() {
		book.setPublisheddate("28 Sept 2022");
		String publisherdate=book.getPublisheddate();
		assertEquals("28 Sept 2022",publisherdate);
		
	}

	
	
	

}
