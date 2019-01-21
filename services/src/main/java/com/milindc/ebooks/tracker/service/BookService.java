package com.milindc.ebooks.tracker.service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.lucene.analysis.en.KStemmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.BookRepository;
import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.assembler.BookAssembler;
import com.milindc.ebooks.tracker.service.model.BookView;
import com.milindc.ebooks.tracker.service.model.Books;

@Component
@Path("/books")
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	BookAssembler bookAssembler = new BookAssembler();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Books getBooks() {
		Books books = new Books();
		Iterable<Book> itr = bookRepository.findAll();

		books.setBooks(
				StreamSupport.stream(itr.spliterator(), false).map(b -> bookAssembler.to(b)).collect(Collectors.toList()));
		System.out.println("Find All books works");
		return books;
	}

	@GET
	@Path("/author/{author}")
	public Books getBookByAuthor(@PathParam("author") String author) {
		System.out.println("Find by author works");
		Books books = new Books();
		List<Book> itr = bookRepository.findByAuthor(author);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		return books;
	}
	
	@GET
	@Path("/isbn/{isbn}")
	public Books getBookByIsbn(@PathParam("isbn") String isbn) {
		Books books = new Books();
		List<Book> itr = bookRepository.findByIsbn(isbn);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		System.out.println("Find by isbn works");
		return books;
	}
	
	@GET
	@Path("/title/{title}")
	public Books getBookByTitle(@PathParam("title") String title) {
		Books books = new Books();
		List<Book> itr = bookRepository.findByTitle(title);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		System.out.println("Find by title works");
		return books;
	}
	
	@GET
	@Path("/genre/{genre}")
	public Books getBookByGenre(@PathParam("genre") String genre) {
		Books books = new Books();
		List<Book> itr = bookRepository.findByGenre(genre);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		System.out.println("Find by author genre");
		return books;
	}
	
	@GET
	@Path("/publicationYear/{publicationYear}")
	public Books getBookBypublicationYear(@PathParam("publicationYear") String publicationYear) {
		Books books = new Books();
		List<Book> itr = bookRepository.findByPublicationYear(publicationYear);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		return books;
	}
	
	@GET
	@Path("/editor/{editor}")
	public Books getBookByEditor(@PathParam("editor") String editor) {
		Books books = new Books();
		List<Book> itr = bookRepository.findByEditor(editor);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		return books;
	}
	
	@GET
	@Path("/publisher/{publisher}")
	public Books getBookByPublisher(@PathParam("publisher") String publisher) {
		Books books = new Books();
		List<Book> itr = bookRepository.findByPublisher(publisher);

		books.setBooks(itr.stream().map(s -> bookAssembler.to(s)).collect(Collectors.toList()));
		return books;
	}
	
	@POST
	@Consumes("application/json")//MediaType.APPLICATION_JSON)
	public Response createBook(BookView bookView, @Context HttpServletRequest request) {
		Book book = bookAssembler.to(bookView);
		book = bookRepository.save(book);
		System.out.println(book);
		BookView savedBook = bookAssembler.to(book);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedBook.getId())).build();
		return Response.created(uri).entity(savedBook).build();
	}
}
