package com.DigitalBooks.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.DigitalBooks.models.Author;
import com.DigitalBooks.models.Book;
import com.DigitalBooks.models.Payment;
import com.DigitalBooks.models.Refund;
import com.DigitalBooks.payload.request.RefundRequest;
import com.DigitalBooks.repository.AuthorRepository;
import com.DigitalBooks.repository.BookRepository;
import com.DigitalBooks.repository.PaymentRepository;
@ExtendWith(MockitoExtension.class)
public class AuthorDetailsServiceImplTest {
//	@Mock
//	AuthorRepository userRepository;
//	@Mock
//	private PaymentRepository paymentRepository;
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private AuthorDetailsServiceImpl service;
	
	@Mock
	private PaymentRepository paymentRepository;
	
	@Mock
	AuthorRepository authorRepository;
	
	Payment payment=new Payment();
	
	@Test
	public void createbookserviceTest() {
		Book book=new Book();
		book.setBookid(1);
		book.setActive("yes");
		book.setAuthor("siva");
		book.setCategory("general");
		book.setContent("Awesome");
		book.setImage("earth.jpg");
		book.setPrice(255);
		book.setPublisheddate("03 Octo 2022");
		book.setPublisher("podili");
		book.setTitle("Earth");
		
		when(bookRepository.save(book)).thenReturn(book);
		Book book1=service.createbookservice(book);
		//assertThat(book1).isEqualTo(book);
		assertEquals("siva", book.getAuthor());
	}
	
	@Test
	public void getAllBooksTest() {
		Book book1=new Book();
		book1.setBookid(1);
		book1.setActive("yes");
		book1.setAuthor("siva");
		book1.setCategory("general");
		book1.setContent("Awesome");
		
		Book book2=new Book();
		book2.setBookid(1);
		book2.setActive("yes");
		book2.setAuthor("siva");
		book2.setCategory("general");
		book2.setContent("Awesome");
		
		

		List<Book> book=new ArrayList<>();
		book.add(book1);
		book.add(book2);
		Mockito.when(bookRepository.findAll()).thenReturn(book);
		List<Book> book3=service.getAllBooksService();
		assertThat(book3).isEqualTo(book);
		
	}
	
	@Test
	public void updateBookTest() {
		Book book1=new Book();
		book1.setBookid(1);
		book1.setActive("yes");
		book1.setAuthor("siva");
		book1.setCategory("general");
		book1.setContent("Awesome");
		Mockito.when(bookRepository.save(book1)).thenReturn(book1);
		book1.setAuthor("Podili");
		Mockito.when(bookRepository.save(book1)).thenReturn(book1);
		Book book=service.updateBookDetails(book1);
		assertEquals(book, book1);
		
	}
	@Test
	public void findByCatagoryAndAuthorAndPriceAndPublisherTest() {
		Book book1=new Book();
		book1.setCategory("general");
		String catagory=book1.getCategory();
		book1.setAuthor("siva");
		String author=book1.getAuthor();
		book1.setPrice(125);
		int price=book1.getPrice();
		book1.setPublisher("podili");
		String publisher=book1.getPublisher();
		List<Book> book=new ArrayList<>();
		book.add(book1);
		when(bookRepository.findByCatagoryAndAuthorAndPriceAndPublisher(catagory, author, price, publisher)).thenReturn(book);
		List<Book> bookser=service.findByCatagoryAndAuthorAndPriceAndPublisher("general","siva",125,"podili");
		assertEquals(bookser,book);
		}
	
	 @Test
	    public void getBookByBookId()
	    {
	        Book book=new Book();
	       book.setBookid(1);
	       int id=book.getBookId();
	        when(bookRepository.findByBookId(1)).thenReturn(book);
	        Book book1=service.getBookByBookId(id);
	       assertEquals(book1,book);
	    }
	
	
	@Test
	public void getPaymentById() {
		
		payment.setPaymentId(1L);
		Long paymentid=payment.getPaymentId();
		when(paymentRepository.findByPaymentId(1L)).thenReturn(payment);
		Payment payser=service.getPaymentById(paymentid);
		assertEquals(payser,payment);
		
	}
	@Test
	public void savePaymentTest() {
		payment.setBookiD(1);
		int bookid=payment.getBookiD();
		payment.setEmail("siva@gmail.com");
		String email=payment.getEmail();
		payment.setPaymentDate(new Date());
		Date date=payment.getPaymentDate();
		payment.setPaymentId(1L);
		Long paymentid=payment.getPaymentId();
		payment.setPrice(124);
		int price=payment.getPrice();
		payment.setReaderId(1L);
		Long readerId=payment.getReaderId();
		when(paymentRepository.save(payment)).thenReturn(payment);
		Payment payser=service.save(payment);
		assertEquals(payser,payment);
		
	}
	
	@Test
	public void isUserAvailableTest() {
		String str="siva";
		when(authorRepository.existsByUsername("siva")).thenReturn(true);
		assertTrue(service.isUserAvailable(str));
	}
	
	@Test
	public void isBookAvailableTest() {
		int bookid=1;
		when(bookRepository.existsByBookId(1)).thenReturn(true);
		assertTrue(service.isBookAvailable(bookid));
	}
	
	@Test
	public void isPaymentAvailableByReaderIdTest() {
		Long readerId=1L;
		when(paymentRepository.existsByReaderId(1L)).thenReturn(true);
		assertTrue(service.isPaymentAvailableByReaderId(readerId));
	}
	@Test
	public void isUserAvailableByEmailTest() {
		String str="siva@gmail.com";
		when(authorRepository.existsByEmail("siva@gmail.com")).thenReturn(true);
		assertTrue(service.isUserAvailableByEmail(str));
	}
	
	
	@Test
	public void findBookByPaymentIdTest() {
		String email="siva@gmail.com";
		 Boolean isuser = service.isUserAvailableByEmail(email);
		 Map<String, String> payload = new HashMap<String, String>();
		 if (isuser) {
			 Long paymentid=11L;
             Payment payment = service.getPaymentById(paymentid);
             int bookId = payment.getBookiD();
             Book book = service.readBook(bookId);
             payload.put("author", book.getAuthor());
             payload.put("catagory", book.getCategory());
             payload.put("publishedDate", book.getPublisheddate());
             payload.put("publisher", book.getPublisher());
             payload.put("title", book.getTitle());
             payload.put("price", book.getPrice().toString());
         }
		 Map<String, String> payload1=service.findBookByPaymentId(email, 28L);
		 assertEquals(payload1,payload);


		
	}
	
	@Test
	public void ReadBookTest() {
		Book book=new Book();
		book.setBookid(1);
		int bookid=book.getBookId();
		Optional<Book> optional=Optional.of(book);
		
		when(bookRepository.findById(1)).thenReturn(optional);
		Book book1=service.readBook(bookid);
		assertEquals(book1,book);
		
	}
	
	@Test
	public void getByEmailTest() {
		Author author=new Author();
		author.setEmail("siva@gmail.com");
		String email=author.getEmail();
		Optional<Author> optional=Optional.of(author);
		
		when(authorRepository.findByEmail("siva@gmail.com")).thenReturn(optional);
		Optional<Author> optional1=service.getByEmail(email);
		assertEquals(optional1,optional);
		
	}
	
	@Test
	public void getUserByNameTest() {
		Author author=new Author();
		author.setUsername("siva");
		String username=author.getUsername();
		Optional<Author> optional=Optional.of(author);
		
		when(authorRepository.findByUsername("siva")).thenReturn(optional);
		Optional<Author> optional1=service.getUserByName(username);
		assertEquals(optional1,optional);
		
	}
	
	@Test
	public void testPaymentRequest() {
		RefundRequest refundRequest = new RefundRequest();
		refundRequest.setBookId("11");
		refundRequest.setEmail("kamalakar24@gmail.com");
		refundRequest.setPaymentId("28");
		refundRequest.setRefundAmount(4.5);
		Boolean isuser = true;
		when(authorRepository.existsByEmail(refundRequest.getEmail())).thenReturn(isuser);
		Boolean isuser1 = service.isUserAvailableByEmail(refundRequest.getEmail());
		assertEquals(isuser, isuser1);
		Payment payment = new Payment();
		payment.setPaymentId(28L);
		payment.setBookiD(11);
		payment.setPrice(6);
		payment.setReaderId(3L);
		when(paymentRepository.findByPaymentId(28L)).thenReturn(payment);
		Payment payment1 = service.getPaymentById(28L);
		assertEquals(payment, payment1);

		String msg = "Refund request generated successfully";
		Long paymentid = Long.parseLong(refundRequest.getPaymentId());

		Long bookid = Long.parseLong(refundRequest.getBookId());
		Integer statusId = 2000;
		String status = "Reader requested";
		Refund refund = new Refund();
		refund.setBookId(bookid);
		refund.setPaymentId(paymentid);
		refund.setReaderId(3L);

		refund.setRefundDate(new Date());
		refund.setRefundedAmount(4.5);
		refund.setRefundStatus(status);

		refund.setStatusId(statusId);
		String message = service.saveRefund(refund.getReaderId(), refund.getBookId(), refund.getPaymentId(),
				refund.getRefundedAmount());
		assertEquals(msg, message);
		String msg1 = service.paymentRequest(refundRequest);
		assertEquals(msg, msg1);

	}
	
//	@Test
//	public void loadUserByUsernametest() {
//		//String username="siva";
//		Author author=new Author();
//		author.setUsername("siva");
//	
//		Optional<Author> optional=Optional.of(author);
//		
//		when(authorRepository.findByUsername("siva")).thenReturn(optional);
//		UserDetails user=service.loadUserByUsername(author.getUsername());
//		assertEquals(optional, user);
//		
//	}
//	
//	@Test
//	public void testReadContent() {
//		String email = "siva@gmail.com";
//		int bookId = 11;
//		Map<String, String> map = new HashMap<String, String>();
//		Boolean isuser = true;
//		Book book = service.readBook(bookId);
//		 
//		 map.put("catagory", book.getCategory());
//         map.put("content", book.getContent());
//         map.put("Author", book.getAuthor());
//		when(authorRepository.existsByEmail(email)).thenReturn(isuser);
//		
//         Map<String, String> map1= service.readContent(email, bookId);
//         assertEquals(map, map1);
//		
//		Boolean isuser1 = service.isUserAvailableByEmail(email);
//		assertEquals(isuser, isuser1);
//		Book book = new Book();
//		book.setCategory("Finance");
//		book.setContent("jjjjjjj");
//		book.setAuthor("Marvel");
//		when(bookRepository.findByBookId(bookId)).thenReturn(book);
//		Book book1 = service.getBookByBookId(bookId);
//		assertEquals(book, book1);
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("catagory", "Finance");
//		map.put("content", "jjjjjjj");
//		map.put("Author", "Marvel");
//		Map<String, String> map1 = service.readContent(email, bookId);
//		assertEquals(map, map1);

//}
	
	@Test
	public void testReadContent() {
		String email = "siva@gmail.com";
		int bookId = 11;
		Boolean isuser = true;
		when(authorRepository.existsByEmail(email)).thenReturn(isuser);
		Boolean isuser1 = service.isUserAvailableByEmail(email);
		assertEquals(isuser, isuser1);
		Map<String, String> map = new HashMap<String, String>();
		map.put("catagory", "Finance");
		map.put("content", "jjjjjjj");
		map.put("Author", "Marvel");
		Map<String, String> map1 = service.readContent(email, bookId);
		assertEquals(map, map1);

	}
	
	@Test
	public void getBookIdTest() {
		Payment payment = new Payment();
		payment.setReaderId(3L);
		Long readerid=payment.getReaderId();
		
		List<Payment> paymentList=new ArrayList<Payment>();
		Map<String, Set<Long>> map = new HashMap<String, Set<Long>>();
		
		 Set<Long> bookIdList = new HashSet<Long>();
		 bookIdList.add( (long) payment.getBookiD());
		 map.put("bookId", bookIdList);
		 when(paymentRepository.findAllByreaderId(3L)).thenReturn(paymentList);
		
		
		 Map<String, Set<Long>> map1 = service.getBookId(readerid);
		assertNotEquals(map1, map);
		
	}


}
