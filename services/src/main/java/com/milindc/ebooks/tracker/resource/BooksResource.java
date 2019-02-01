package com.milindc.ebooks.tracker.resource;


import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.BookService;
import com.milindc.ebooks.tracker.service.assembler.BookAssembler;
import com.milindc.ebooks.tracker.service.model.BookView;
import com.milindc.ebooks.tracker.service.model.Books;

import lombok.extern.slf4j.Slf4j;

@Component
@Path("/books")
@Slf4j
public class BooksResource {
	@Autowired
	private BookService bookService;
	BookAssembler bookAssembler = new BookAssembler();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks() {
		Books books = new Books();
		Iterable<Book> itr = bookService.getBooks();
		books.setBooks(StreamSupport.stream(itr.spliterator(), false).map(b -> bookAssembler.to(b)).collect(Collectors.toList()));
		log.debug("Find All books works");
		return Response.ok(books).build();
	}



	@GET
	public Response getBookByAuthor(@QueryParam("author") String author, @QueryParam("isbn") String isbn, 
			@QueryParam("title") String title, @QueryParam("genre") String genre, 
			@QueryParam("publicationYear") String publicationYear, @QueryParam("editor") String editor, 
			@QueryParam("publisher") String publisher) {
		Books books = new Books();
		Iterable<Book> itr = bookService.getBooks(author, isbn, title, genre, publicationYear, editor, publisher);
		books.setBooks(StreamSupport.stream(itr.spliterator(), false).map(b -> bookAssembler.to(b)).collect(Collectors.toList()));
		log.debug("Find books by query param works");
		return Response.ok(books).build();
	}
	
	@POST
	@Consumes("application/json")//MediaType.APPLICATION_JSON)
	public Response createBook(BookView bookView, @Context HttpServletRequest request) {
		Book book = bookAssembler.to(bookView);
		book = bookService.createBook(book);
		log.debug(String.format("Retrieved book %s", book));
		BookView savedBook = bookAssembler.to(book);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedBook.getId())).build();
		return Response.created(uri).entity(savedBook).build();
	}
}
