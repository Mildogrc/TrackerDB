package com.milindc.ebooks.tracker.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.milindc.ebooks.tracker.db.model.Checkout;


@Repository
public interface CheckoutRepository  extends JpaRepository<Checkout, Long>, JpaSpecificationExecutor<Checkout> {

	@Query("SELECT c FROM Checkout c where c.student.id = :studentId")
	List<Checkout> findByStudentId(Long studentId);

	@Query("SELECT c FROM Checkout c where c.book.id = :bookId")
	List<Checkout> findByBookId(Long bookId);

}
