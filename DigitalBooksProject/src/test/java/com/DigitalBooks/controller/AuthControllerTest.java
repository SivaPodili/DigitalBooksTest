package com.DigitalBooks.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.DigitalBooks.models.Author;
import com.DigitalBooks.models.Book;
import com.DigitalBooks.models.ERole;
import com.DigitalBooks.models.Payment;
import com.DigitalBooks.models.Role;
import com.DigitalBooks.payload.request.BookRequest;
import com.DigitalBooks.payload.request.LoginRequest;
import com.DigitalBooks.payload.request.RefundRequest;
import com.DigitalBooks.payload.request.SignupRequest;
import com.DigitalBooks.payload.response.MessageResponse;
import com.DigitalBooks.repository.AuthorRepository;
import com.DigitalBooks.repository.RoleRepository;
import com.DigitalBooks.security.jwt.JwtUtils;
import com.DigitalBooks.security.service.AuthorDetailsImpl;
import com.DigitalBooks.security.service.AuthorDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;


@SpringBootTest
public class AuthControllerTest {
	@Mock
	 AuthorDetailsServiceImpl service;
	 @InjectMocks
	 AuthController controller;
	 @Mock
	 AuthorRepository authorRepository;
	 @Mock
	 RoleRepository roleRepository;
	 @Mock
	PasswordEncoder encoder;
	 
	 @Test
		public void testAuthenticateUser() {
			LoginRequest loginRequest= new LoginRequest();
			loginRequest.setUsername("sivapodili");
			loginRequest.setPassword("password");
			Authentication authToken = new UsernamePasswordAuthenticationToken (loginRequest.getUsername(), loginRequest.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
	 
	 @Test
		public void testRegisterUsertWithExistingUser() {
			SignupRequest signUpRequest = new SignupRequest();
			Set<Role> rolelist= new HashSet();
			Role role= new Role();
			role.setName(ERole.ROLE_AUTHOR);
			signUpRequest.setUsername("siva");
			signUpRequest.setPassword("password");
			signUpRequest.setEmail("siva@gmail.com");
			rolelist.add(role);
			when(authorRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);
			ResponseEntity responseEntity= ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error:  Username is already taken!"));
			ResponseEntity responseEntity1= controller.registerUser(signUpRequest);
			assertNotNull(responseEntity1);
		}
	 
	 @Test
		public void testRegisterUsertWithExistinEmail() {
			SignupRequest signUpRequest = new SignupRequest();
			Set<Role> rolelist= new HashSet();
			Role role= new Role();
			role.setName(ERole.ROLE_AUTHOR);
			signUpRequest.setUsername("siva");
			signUpRequest.setPassword("password");
			signUpRequest.setEmail("siva@gmail.com");
			rolelist.add(role);
			when(authorRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
			ResponseEntity responseEntity= ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
			ResponseEntity responseEntity1= controller.registerUser(signUpRequest);
			assertNotNull(responseEntity1);
		}
	 
	 @Test
		public void testRegisterUserWithDefaultUser() {
			SignupRequest signUpRequest = new SignupRequest();
			Set<Role> rolelist= new HashSet();
			signUpRequest.setUsername("siva");
			signUpRequest.setPassword("password");
			signUpRequest.setEmail("siva@gmail.com");			
			// Create new user's account
					Author user = new Author(signUpRequest.getUsername(), 
										 signUpRequest.getEmail(),
										 encoder.encode(signUpRequest.getPassword()));

					Set<String> strRoles = signUpRequest.getRole();
					Set<Role> roles = new HashSet<>();
					when(signUpRequest.getRole()).thenReturn(null);
					
					Role readerRole = new Role();
					readerRole.setName(ERole.ROLE_AUTHOR);
					Optional<Role> optioanl=Optional.of(readerRole);
					when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(optioanl);
					ResponseEntity responseEntity=ResponseEntity.ok(new MessageResponse("Reader"+"  registered is  successfully!"));
					ResponseEntity responseEntity1=controller.registerUser(signUpRequest);
	                assertNotNull(responseEntity1);
		}
		
	 @Test
		public void testRegisterUserWithDefaultUserReader() {
			SignupRequest signUpRequest = new SignupRequest();
			Set<Role> rolelist= new HashSet();
			Role role= new Role();
			role.setName(ERole.ROLE_READER);
			signUpRequest.setUsername("sivar");
			signUpRequest.setPassword("password");
			signUpRequest.setEmail("siva@gmail.com");	
			rolelist.add(role);
		
			// Create new user's account
					Author user = new Author(signUpRequest.getUsername(), 
										 signUpRequest.getEmail(),
										 encoder.encode(signUpRequest.getPassword()));

					Set<String> strRoles = signUpRequest.getRole();
					Set<Role> roles = new HashSet<>();
					when(signUpRequest.getRole()).thenReturn(strRoles);
					
					Role readerRole = new Role();
					readerRole.setName(ERole.ROLE_READER);
					Optional<Role> optioanl=Optional.of(readerRole);
					when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(optioanl);
					ResponseEntity responseEntity=ResponseEntity.ok(new MessageResponse("Reader"+"  registered is  successfully!"));
					ResponseEntity responseEntity1=controller.registerUser(signUpRequest);
	                assertNotNull(responseEntity1);
		}
	 
	 
	 @Test
	 public void createBookTest() {
		 Book book=new Book();
		 book.setAuthor("siva");
		 book.setBookid(1);
		 book.setPrice(125);
		 book.setContent("Earth");
		 when(service.createbookservice(book)).thenReturn(book);
		 ResponseEntity entity= controller.createBook(book);
		 assertThat(entity.ok(new MessageResponse("Book is created succefully")));
		 }
	 
	 
	 @Test
	    public void getAllBooksTest()
	    {
	        List<Book> book=new ArrayList<>();
	        when(service.getAllBooksService()).thenReturn(book);
	        List<Book> result=controller.getAllBooks();
	        assertThat(result.equals(book));
	    }
	    @Test
	    public void searchByBookIdTest()
	    {
	        Book book=new Book();
	       book.setBookid(1);
	       int id=book.getBookId();
	        when(service.getBookByBookId(1)).thenReturn(book);
	        Book book1=controller.readBook(1);
	       assertEquals(1,id);
	    }

//	    @Test
//	    public void searchbycatpriceauthpublicTest() throws JsonProcessingException{
//	    	Map<String, String> result = new HashMap<>();
//	    	List<Book> list=new ArrayList<Book>();
//	    	 when(service.findByCatagoryAndAuthorAndPriceAndPublisher("author", "catagory", 150, "publisher")).thenReturn(list);
//	    	 ResponseEntity entity = controller.SearchBooks("author", "catagory", "275", "publisher");
//	    	assertThat(entity.equals(result));
//	    	
//}
	    @Test
	    public void updateBookDetailsTest()
	    {
	        Book book=new Book();
	        book.setBookid(2);
	        
	        when(service.updateBookDetails(book)).thenReturn(book);
	    Book book1 = controller.updateBook(book, 2);
	    assertEquals(book,book1);
	         
	    }
	    
	    @Test
	    public void readBookByEmailandBookidTest() throws JsonProcessingException {
	    	Map<String, String> map = new HashMap<String, String>();
	    	Book book=new Book();
	    	map.put("book is generated::",String.valueOf(book.getBookId()));
	    	when(service.readContent("siva@gmail.com", 1)).thenReturn(map);
	    	ResponseEntity entity =controller.readBooks("siva@gmail.com", String.valueOf(1));
	    	assertThat(entity.equals(map));
	    	
	    }
	    
	    @Test
	    public void readBookByEmailandPaymentidTest() throws JsonProcessingException {
	    	Map<String, String> map = new HashMap<String, String>();
	    	Book book=new Book();
	    	when(service.findBookByPaymentId("siva@gmail.com", 1L)).thenReturn(map);
	    	ResponseEntity entity =controller.getBookByPaymentid("siva@gmail.com", String.valueOf(1L));
	    	assertThat(entity.equals(map));
	    	
	    }
	    
	    @Test
		public void buyBooksTest() {
			BookRequest request= new BookRequest();
			request.setBookid("11");
			String bookid=request.getBookid();
			request.setEmail("siva@gmail.com");
			String email=request.getEmail();
			request.setUsername("siva");
			String username=request.getUsername();
			int bookId=Integer.parseInt(request.getBookid());
			when(service.isUserAvailable("siva")).thenReturn(true);
	
			when(service.isBookAvailable(11)).thenReturn(true);
			Book book = new Book();
			book.setActive("true");
			book.setAuthor("Marvel");
			book.setCategory("Finance");
			book.setContent("HHHHHHHHHHHHH");
			book.setBookid(11);
			book.setPrice(40);
			book.setPublisher("lhlllll");
			when(service.getBookByBookId(bookId)).thenReturn(book);
			Author user = new Author();
			user.setEmail(request.getEmail());
			user.setUsername(request.getUsername());
			user.setId(3L);
			Optional<Author> optionalUser=Optional.of(user);
			when(service.getUserByName(request.getUsername())).thenReturn(optionalUser);
			Payment payment=new Payment();
			payment.setBookiD(11);
			payment.setReaderId(3L);
			payment.setPaymentDate(new Date());
			payment.setPaymentId(1L);
		
			
			when(service.save(payment)).thenReturn(payment);
			Map<String,Long> respayload= new HashMap<String,Long>();
	
			respayload.put("pamentId", payment.getPaymentId());
			respayload.put("bookId", (long) payment.getBookiD());
			
		
				
		ResponseEntity responseEntity = new ResponseEntity(respayload , HttpStatus.OK);
	
		ResponseEntity responseEntity1 = controller.buyBooks(request);
	
	assertNotNull(responseEntity1);
		
		
		}
	    
	    @Test
		public void searchBook() throws JsonProcessingException {
			
			Book book = new Book();
			book.setAuthor("author");
			String author1=book.getAuthor();
			book.setCategory("catagory");
			String catagory1=book.getCategory();
			book.setPublisher("publisher");
			String publisher=book.getPublisher();
			book.setPrice(150);
			int price2=book.getPrice();
			
			book.setPublisheddate("publishedDate");
			book.setTitle("title");
			
			List<Book> bookList = new ArrayList<>();
			bookList.add(book);
			
			 when(service.findByCatagoryAndAuthorAndPriceAndPublisher("author", "catagory", 150, "publisher")).thenReturn(bookList);
			 
			Map<String,String> payload= new HashMap<String,String>();
			payload.put("author",book.getAuthor());
			payload.put("catagory",book.getCategory());
			payload.put("publishedDate",book.getPublisheddate());
			payload.put("publisher",book.getPublisher());
			payload.put("title",book.getTitle());
			payload.put("price",book.getPrice().toString());

			ResponseEntity responseEntity = new ResponseEntity(payload , HttpStatus.OK);
			
			List<Book> book1=controller.SearchBooks(author1, catagory1, String.valueOf(price2), publisher);
			assertNotNull(book1);
			
		}
	    
	    @Test
	    public void findAllPurchasedBooksTest() {
	    	String email="siva@gmail.com";
	    	Author user = new Author();
	    	Optional<Author> optional = Optional.of(user);
	    	when(service.isPaymentAvailableByReaderId(user.getId())).thenReturn(true);
	    	Map<String, Set<Long>> bookList = service.getBookId(user.getId());
	    	ResponseEntity responseEntity = new ResponseEntity(bookList, HttpStatus.OK);
	    	ResponseEntity entity=controller.findAllPurchasedBooks(email);
	    	assertThat(entity).isEqualTo(responseEntity);
	    	
	    }
	    

		@Test
		public void testRefund() {
			String msge="Refund request generated successfully";
			Map<String,String> mapString = new HashMap<String,String>();
			RefundRequest refundRequest = new RefundRequest();
			refundRequest.setBookId("11");
			refundRequest.setEmail("debaprasad@gmail.com");
			refundRequest.setPaymentId("28");
			refundRequest.setRefundAmount(4.5);
			mapString.put("Decription", msge);
			when(service.paymentRequest(refundRequest)).thenReturn(msge);
			ResponseEntity responseEntity = new ResponseEntity(mapString ,HttpStatus.OK);
			ResponseEntity responseEntity1 = controller.refund(refundRequest);
			assertEquals(responseEntity, responseEntity1);

	       }
		
}
