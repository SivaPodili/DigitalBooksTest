package com.DigitalBooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DigitalBooks.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	Boolean existsByReaderId(Long readerId);

	List<Payment> findAllByreaderId(Long readerid);

	Payment findByPaymentId(Long paymentId);

}
