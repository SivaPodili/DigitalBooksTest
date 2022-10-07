package com.DigitalBooks.security.service;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DigitalBooks.models.Author;
import com.DigitalBooks.models.Book;
import com.DigitalBooks.models.Payment;
import com.DigitalBooks.models.Refund;
import com.DigitalBooks.payload.request.RefundRequest;
import com.DigitalBooks.repository.BookRepository;
import com.DigitalBooks.repository.PaymentRepository;
import com.DigitalBooks.repository.RefundRepository;
import com.DigitalBooks.repository.AuthorRepository;



@Service
public class AuthorDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AuthorRepository userRepository;
	
	@Autowired
	private BookRepository authorCreateBookRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private RefundRepository refundRepository;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Author user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return AuthorDetailsImpl.build(user);
	}
	
	//Below all methods refer to Author Role
	
	//Author can create book
	public Book createbookservice(Book authorCreateBook) {
		
		return authorCreateBookRepository.save(authorCreateBook);
	
		
	}


	//Author gets all the book details
	public List<Book> getAllBooksService() {
		
		return authorCreateBookRepository.findAll();
	}

	
	//Author can update/Edit the book based on bookid
	public Book updateBookDetails(Book authorCreateBook) {
		return authorCreateBookRepository.save(authorCreateBook);
				
	}

	//Below all methods refer to Reader Role
	//Reader can Search Books
	public List<Book> findByCatagoryAndAuthorAndPriceAndPublisher(String category, String author, Integer priceInt,
            String publisher) {
        
        return authorCreateBookRepository.findByCatagoryAndAuthorAndPriceAndPublisher( category,  author,  priceInt,
                 publisher);
    }
	
	

	//Reader can read book by using bookid
	public Book readBook(int bookId) {
		return authorCreateBookRepository.findById(bookId).get();
	}
	
	//Reader can find all purchased books
	
	   public Optional<Author> getByEmail(String email) {
           return userRepository.findByEmail(email);
       }
	   public Boolean isPaymentAvailableByReaderId(Long readerid) {
           Boolean paymentAvaible = paymentRepository.existsByReaderId(readerid);
           return paymentAvaible;
}
	   
	    public Map<String, Set<Long>> getBookId(Long readerid) {
           List<Payment> paymentList = paymentRepository.findAllByreaderId(readerid);
           Set<Long> bookIdList = new HashSet<Long>();
           Map<String, Set<Long>> map = new HashMap<String, Set<Long>>();
           paymentList.forEach(payment -> {
               bookIdList.add((long) payment.getBookiD());
           });
           map.put("bookId", bookIdList);
           return map;
       }
	   
	  //Reader can read book
	   public Map<String, String> readContent(String email, int bookId) {
           Boolean isuser = isUserAvailableByEmail(email);
           Map<String, String> map = new HashMap<String, String>();
           if (isuser) {
               Book book = readBook(bookId);
               System.out.println("book is generated::" + book.getAuthor());
               map.put("catagory", book.getCategory());
               map.put("content", book.getContent());
               map.put("Author", book.getAuthor());
           }
           return map;
       }
	   public Boolean isUserAvailableByEmail(String email) {
           return userRepository.existsByEmail(email);



      }

	 //reader can find the purchased book by paymentid
	   public Map<String, String> findBookByPaymentId(String email, Long payemntId) {
           Boolean isuser = isUserAvailableByEmail(email);
           Map<String, String> payload = new HashMap<String, String>();
           if (isuser) {
               Payment payment = getPaymentById(payemntId);
               int bookId = payment.getBookiD();
               Book book = readBook(bookId);
               payload.put("author", book.getAuthor());
               payload.put("catagory", book.getCategory());
               payload.put("publishedDate", book.getPublisheddate());
               payload.put("publisher", book.getPublisher());
               payload.put("title", book.getTitle());
               payload.put("price", book.getPrice().toString());
           }
           return payload;
       }
	   
	   
	   
	   public Payment getPaymentById(Long paymentId) {
           Payment payment = paymentRepository.findByPaymentId(paymentId);
           return payment;
       }
	   
	   //Reader can Buy Book
	   public Payment save(Payment payment) {
           return paymentRepository.save(payment);

 }
	   
	   public Boolean isUserAvailable(String userName) {
           Boolean isUserAvailable = userRepository.existsByUsername(userName);
           return isUserAvailable;
	   }
	   
	   public Boolean isBookAvailable(int bookId) {
           Boolean isBookAvaiable = authorCreateBookRepository.existsByBookId(bookId);
           return isBookAvaiable;
       }
	   
	   public Book getBookByBookId(int bookId) {
           return authorCreateBookRepository.findByBookId(bookId);
       }


     public Optional<Author> getUserByName(String username) {
           return userRepository.findByUsername(username);
       }

//Refund
     public String paymentRequest(RefundRequest refundRequest) {
         Long paymentid = Long.parseLong(refundRequest.getPaymentId());



        Long bookid = Long.parseLong(refundRequest.getBookId());



        String msg = "";



        if (refundRequest.getEmail() != null && refundRequest.getBookId() != null
                 && refundRequest.getPaymentId() != null) {
             Boolean isUser = isUserAvailableByEmail(refundRequest.getEmail());
             if (isUser) {
                 Payment payment = getPaymentById(paymentid);
                 Long readerid = payment.getReaderId();
                 Integer price = payment.getPrice();
                 System.out.println("readerid" + readerid + "bookid" + bookid + "paymentid"
                         + paymentid + "refundRequest.getRefundAmount()   "
                         + refundRequest.getRefundAmount());
                 msg = saveRefund(readerid, bookid, paymentid, refundRequest.getRefundAmount());
             } else {
                 msg = "User is not available";
             }
         } else {
             msg = "PaymentId,BookId and email Should be mandatory";
         }
         return msg;
     }
     
     public String saveRefund(Long readerId, Long bookId, Long paymentId, Double refundAmount) {
         try {
             Integer statusId = 2000;
             String status = "Reader requested";
             Refund refund = new Refund();
             refund.setBookId(bookId);
             refund.setPaymentId(paymentId);
             refund.setReaderId(readerId);
             System.out.println(
                     "Aftyer readerId@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
             refund.setRefundDate(new Date());
             refund.setRefundedAmount(refundAmount);
             refund.setRefundStatus(status);
             System.out.println("After status@@@@@@@@@@@@@@@@@@@@");
             refund.setStatusId(statusId);
             System.out.println(refund + "   Refund object");
             System.out.println(refund.getRefundStatus() + "BookId##" + refund.getBookId()
                     + "paymentId:  " + refund.getPaymentId());
             System.out.println("refundId: " + refund.getReaderId() + "statusId: "
                     + refund.getStatusId() + "staus" + refund.getRefundStatus());
             refundRepository.save(refund);
         } catch (Exception e) {
             e.printStackTrace();
         }



        return "Refund request generated successfully";



    }
	   
	   
}
