package com.DigitalBooks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.DigitalBooks.models.Author;
import com.DigitalBooks.models.Book;
import com.DigitalBooks.models.Payment;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	 List<Book> findByCatagoryAndAuthorAndPriceAndPublisher(String category, String author, Integer priceInt, String publisher);
   
	Boolean existsByBookId(int bookId);

	Book findByBookId(int bookId);
	

	

	

}
