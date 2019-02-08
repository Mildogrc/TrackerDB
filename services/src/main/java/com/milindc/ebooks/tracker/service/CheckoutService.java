package com.milindc.ebooks.tracker.service;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.CheckoutRepository;
import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.db.model.Checkout;
import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.assembler.CheckoutAssembler;
import com.milindc.ebooks.tracker.service.model.CheckoutView;
import com.milindc.ebooks.tracker.service.model.Checkouts;

import lombok.extern.slf4j.Slf4j;

@Path("/checkouts")
@Component
@Slf4j
public class CheckoutService {
	@Autowired
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	private BookService bookService;

	@Autowired
	private StudentService studentService;
	
	private CheckoutAssembler checkoutAssembler;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCheckouts() {
		Checkouts checkouts = new Checkouts();
		Iterable<Checkout> itr = checkoutRepository.findAll();
		checkouts.setCheckouts(StreamSupport.stream(itr.spliterator(), false).map(b -> checkoutAssembler.to(b)).collect(Collectors.toList()));
		log.debug("Find All checkouts works");
		return Response.ok(checkouts).build();
	}

	public List<Checkout> getCheckoutByIsbn(String isbn) {
		Book book = bookService.findByIsbn(isbn);
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
	@POST
	@Consumes("application/json")//MediaType.APPLICATION_JSON)
	public Response checkout(CheckoutView checkoutView, @Context HttpServletRequest request) {
		Checkout checkout = checkoutAssembler.to(checkoutView);
		checkout.setRedemptionCode(UUID.randomUUID().toString());
		checkout.setBook(bookService.findByIsbn(checkout.getBook().getIsbn()));
		checkout.setStudent(studentService.getStudentByStudentId(checkout.getStudent().getStudentId()));
		checkout = checkoutRepository.save(checkout);
		log.debug(String.format("Retrieved checkout %s", checkout));
		CheckoutView savedCheckout = checkoutAssembler.to(checkout);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedCheckout.getRedemptionCode())).build();
		return Response.created(uri).entity(savedCheckout).build();
	}

}
