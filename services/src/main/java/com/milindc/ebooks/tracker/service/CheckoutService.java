package com.milindc.ebooks.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.CheckoutRepository;
import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.db.model.Checkout;
import com.milindc.ebooks.tracker.db.model.Student;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckoutService {
	@Autowired
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	private BookService bookService;

	@Autowired
	private StudentService studentService;
	

	public Iterable<Checkout> getCheckouts() {
		Iterable<Checkout> checkouts = checkoutRepository.findAll();

		log.debug("Find All checkouts works");
		return checkouts;
	}

	public List<Checkout> getCheckoutByIsbn(String isbn) {
		Book book = bookService.getBookByIsbn(isbn);
		List<Checkout> checkouts = checkoutRepository.findByBookId(book.getId());
		log.debug("Find by isbn works");
		return checkouts;
	}

	public List<Checkout> getCheckoutByBookId(Long bookId) {
		List<Checkout> checkouts = checkoutRepository.findByBookId(bookId);
		log.debug("Find by author works");
		return checkouts;
	}
	
	public List<Checkout> getCheckoutByStudent(String studentId) {
		Student student = studentService.getStudentByStudentId(studentId);
		List<Checkout> checkouts = checkoutRepository.findByStudentId(student.getId());
		return checkouts;
	}
	
	public Checkout createCheckout(Checkout checkout) {
		checkout.setBook(bookService.getBookByIsbn(checkout.getBook().getIsbn()));
		checkout.setStudent(studentService.getStudentByStudentId(checkout.getStudent().getStudentId()));
		checkout = checkoutRepository.save(checkout);
		log.debug(String.format("Retrieved checkout %s", checkout));
		return checkout;
	}

}
