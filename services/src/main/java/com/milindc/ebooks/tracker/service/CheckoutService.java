package com.milindc.ebooks.tracker.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.CheckoutRepository;
import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.db.model.Checkout;
import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.assembler.CheckoutAssembler;
import com.milindc.ebooks.tracker.service.model.CheckoutView;
import com.milindc.ebooks.tracker.service.model.Checkouts;
import com.milindc.ebooks.tracker.service.model.StudentView;

@Component
@Path("/checkouts")
public class CheckoutService {
	
	private static final transient Logger log = org.slf4j.LoggerFactory.getLogger(CheckoutService.class);
	
	@Autowired
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	private BookService bookService;

	@Autowired
	private StudentService studentService;
	
	private CheckoutAssembler checkoutAssembler = new CheckoutAssembler();

	@GET
	@Path("${id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCheckout(@PathParam("id") Long id) {
		
		Response response = null;
		Optional<Checkout> checkouts = checkoutRepository.findById(id);
		
		if(checkouts.isPresent()) {
			response = Response.ok(checkoutAssembler.to(checkouts.get())).build();
		} else {
			response = Response.noContent().build();
		}
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCheckouts(@QueryParam("isbn") String isbn, @QueryParam("studentId") String studentId) {
		
		Response response = null;
		Checkouts checkouts = new Checkouts();
		Book book = bookService.findByIsbn(isbn);
		Iterable<Checkout> itr = null;
		if(book != null) {
			itr = checkoutRepository.findByBookId(book.getId());
		} else {
			StudentView student = studentService.getStudentByStudentId(studentId);
			if(student != null) {
				itr = checkoutRepository.findByStudentId(student.getId());
			}
		}
		if(itr != null) {
			checkouts.setCheckouts(StreamSupport.stream(itr.spliterator(), false).map(b -> checkoutAssembler.to(b)).collect(Collectors.toList()));
			if (checkouts.getCheckouts().size() > 0) {
				log.debug("Find All checkouts works");
				response = Response.ok(checkouts).build();
			}
		}
		if(response == null) {
			response = Response.noContent().build();
		}
		return response;
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
		Student student = studentService.findStudentByStudentId(studentId);
		List<Checkout> checkouts = checkoutRepository.findByStudentId(student.getId());
		return checkouts;
	}
	@POST
	@Consumes("application/json")//MediaType.APPLICATION_JSON)
	public Response checkout(CheckoutView checkoutView, @Context HttpServletRequest request) {
		Checkout checkout = checkoutAssembler.to(checkoutView);
		checkout.setRedemptionCode(UUID.randomUUID().toString());
		checkout.setBook(bookService.findByIsbn(checkout.getBook().getIsbn()));
		checkout.setStudent(studentService.findStudentByStudentId(checkout.getStudent().getStudentId()));
		checkout = checkoutRepository.save(checkout);
		log.debug(String.format("Retrieved checkout %s", checkout));
		CheckoutView savedCheckout = checkoutAssembler.to(checkout);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedCheckout.getRedemptionCode())).build();
		return Response.created(uri).entity(savedCheckout).build();
	}

}
