package com.DigitalBooks.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DigitalBooks.models.Author;
import com.DigitalBooks.models.Book;
import com.DigitalBooks.models.ERole;
import com.DigitalBooks.models.Payment;
import com.DigitalBooks.models.Role;
import com.DigitalBooks.payload.request.BookRequest;
import com.DigitalBooks.payload.request.LoginRequest;
import com.DigitalBooks.payload.request.RefundRequest;
import com.DigitalBooks.payload.request.SignupRequest;
import com.DigitalBooks.payload.response.JwtResponse;
import com.DigitalBooks.payload.response.MessageResponse;
import com.DigitalBooks.payload.response.UserResponse;
import com.DigitalBooks.repository.AuthorRepository;
import com.DigitalBooks.repository.RoleRepository;
import com.DigitalBooks.security.jwt.JwtUtils;
import com.DigitalBooks.security.service.AuthorDetailsImpl;
import com.DigitalBooks.security.service.AuthorDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/digitalbooks/author")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private AuthorDetailsServiceImpl authorDetailsServiceImpl;

	@Autowired
	AuthorRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	Author author = new Author();
	
	
//Author Signin
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		AuthorDetailsImpl userDetails = (AuthorDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	String str="";
//Author Signin
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Author user = new Author(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_READER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            str="READER";
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "ROLE_AUTHOR":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    str="AUTHOR";
                    break;  
                
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_READER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                    str="READER";
                }
            });
        }
		user.setRoles(roles);
		userRepository.save(user);

		// return ResponseEntity.of(Optional.of(user));
		//return ResponseEntity.ok(new UserResponse(HttpStatus.OK));
		return ResponseEntity.ok(new MessageResponse(str+"  registered Successfully"));

	}
	
	//Below all methods refer to Author Role

	//Author can create book
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PostMapping("/createbook")
	public ResponseEntity<?> createBook(@RequestBody Book authorCreateBook) {
		authorDetailsServiceImpl.createbookservice(authorCreateBook);
		// return ResponseEntity.of(Optional.of(book));
		return ResponseEntity.ok(new UserResponse(HttpStatus.OK));

	}

	//Author gets all the book details
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@GetMapping("/getallbooks")
	public List<Book> getAllBooks() {
		return authorDetailsServiceImpl.getAllBooksService();

	}

	//Author can update/Edit the book based on bookid
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PutMapping("/books/{bookid}")
	public Book updateBook(@RequestBody Book authorCreateBook, @PathVariable("bookid") int bookId) {
		authorCreateBook.setBookid(bookId);
		authorDetailsServiceImpl.updateBookDetails(authorCreateBook);
		return authorCreateBook;
	}
	
	
	
	//Below all methods refer to Reader Role

	//Reader can Search Books
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/searchBooks")
	@ResponseBody
	public List<Book> SearchBooks(@RequestParam String category, @RequestParam String author,
			@RequestParam String price, @RequestParam String publisher) throws JsonProcessingException {

		Integer priceInt = Integer.parseInt(price);
		List<Book> BooksList = authorDetailsServiceImpl.findByCatagoryAndAuthorAndPriceAndPublisher(category, author,
				priceInt, publisher);
		Map<String, String> result = new HashMap<>();
		BooksList.forEach(Book -> {
			result.put("author", Book.getAuthor());
			result.put("catagory", Book.getCategory());
			result.put("publisheddate", Book.getPublisheddate());
			result.put("publisher", Book.getPublisher());
			result.put("title", Book.getTitle());
			result.put("price", String.valueOf(Book.getPrice()));

		});
		ResponseEntity responseEntity = new ResponseEntity(result, HttpStatus.OK);
		return BooksList;
	}
	
	//Reader can Buy Book
		@PreAuthorize("hasRole('ROLE_READER')")
		@PostMapping("/buyBooks")
		@ResponseBody
		public ResponseEntity buyBooks(@Valid @RequestBody BookRequest bookRequst) {

			int price1 = Integer.parseInt(bookRequst.getBookid());
			Boolean isUserAvailable = authorDetailsServiceImpl.isUserAvailable(bookRequst.getUsername());
			Boolean isBookAvailable = authorDetailsServiceImpl.isBookAvailable(price1);
			Map<String, Long> respayload = new HashMap<String, Long>();

			if (isUserAvailable && isBookAvailable) {
				Book book = authorDetailsServiceImpl.getBookByBookId(price1);
				Optional<Author> optional = authorDetailsServiceImpl.getUserByName(bookRequst.getUsername());

				Payment payment = new Payment();
				Author user = optional.get();

				payment.setPaymentDate(new Date());
				payment.setPrice(book.getPrice());
				payment.setBookiD(book.getBookId());
				payment.setReaderId(user.getId());
				payment = authorDetailsServiceImpl.save(payment);

				respayload.put("pamentId", payment.getPaymentId());
				respayload.put("bookId", (long) payment.getBookiD());

			}
			ResponseEntity responseEntity = new ResponseEntity(respayload, HttpStatus.OK);

			return responseEntity;
		}

		
		
//Reader can find all purchased books
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/allPurchasedBooks/{email}")
	@ResponseBody
	public ResponseEntity findAllPurchasedBooks(@PathVariable("email") String email) {

		Optional<Author> optional = authorDetailsServiceImpl.getByEmail(email);

		Author user = optional.get();

		Boolean isReaderPurchased = authorDetailsServiceImpl.isPaymentAvailableByReaderId(user.getId());
		if (isReaderPurchased) {

		}

		Map<String, Set<Long>> bookList = authorDetailsServiceImpl.getBookId(user.getId());

		ResponseEntity responseEntity = new ResponseEntity(bookList, HttpStatus.OK);

		return responseEntity;
	}

	//Reader can read book
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/readers/{email}/books/{bookId}")
	@ResponseBody
	public ResponseEntity readBooks(@PathVariable("email") String email, @PathVariable("bookId") String bookId)
			throws JsonProcessingException {

		int bookId1 = Integer.parseInt(bookId);
		Map<String, String> mapString = authorDetailsServiceImpl.readContent(email, bookId1);

		ResponseEntity responseEntity = new ResponseEntity(mapString, HttpStatus.OK);

		return responseEntity;
	}
	
	//reader can find the purchased book by paymentid
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("/readers/{email}/books/paymentId/{paymentId}")
	@ResponseBody
	public ResponseEntity getBookByPaymentid(@PathVariable("email") String email,
			@PathVariable("paymentId") String paymentId) throws JsonProcessingException {

		Long payemntid = Long.parseLong(paymentId);
		Map<String, String> mapString = authorDetailsServiceImpl.findBookByPaymentId(email, payemntid);

		ResponseEntity responseEntity = new ResponseEntity(mapString, HttpStatus.OK);

		return responseEntity;
	}
	
	
	//Refund 
	 @PreAuthorize("hasRole('ROLE_READER')")
	    @PutMapping("/refund")
	    @ResponseBody
	    public ResponseEntity refund(@RequestBody RefundRequest refundRequest) {
	        Map<String, String> mapString = new HashMap<String, String>();
	        System.out.println("Refund request:###" + refundRequest);
	        String msge = authorDetailsServiceImpl.paymentRequest(refundRequest);
	        if (msge != null) {
	            mapString.put("Decription", msge);
	        }
	       ResponseEntity responseEntity = new ResponseEntity(mapString, HttpStatus.OK);
	       return responseEntity;
	    }
	
	//Reader can read book by using bookid
		@PreAuthorize("hasRole('ROLE_READER')")
		@GetMapping("/books/{bookid}")
		public Book readBook(@PathVariable("bookid") int bookId) {

			return authorDetailsServiceImpl.readBook(bookId);

		}
	
		
	
}
