package com.milindc.ebooks.tracker.service;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.milindc.ebooks.tracker.db.BookRepository;
import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.assembler.BookAssembler;
import com.milindc.ebooks.tracker.service.model.BookView;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

@Component
@Path("/books")
@Slf4j
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	private BookAssembler bookAssembler = new BookAssembler();

	@GET
	@Path("/${isbn}")
	public Response getBookById(@PathParam("isbn") String isbn) {
		Book book = findByIsbn(isbn);
		if(book != null) {
			BookView bookView = bookAssembler.to(book);
			return Response.ok(bookView).build();
		} else {
			return Response.noContent().build();
		}

	}

	public Book findByIsbn(String isbn) {
		System.out.println("Looking up by " + isbn);
		Book book = bookRepository.findByIsbn(isbn);
		return book;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response createBook(BookView bookView, @Context HttpServletRequest request) {
		Book book = bookAssembler.to(bookView);
		book = saveBookByIsbn(book.getIsbn());
		log.debug(String.format("Retrieved book %s", book));
		BookView savedBook = bookAssembler.to(book);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedBook.getId())).build();
		return Response.created(uri).entity(savedBook).build();
	}

	public Book saveBookByIsbn(String isbn) {

		Book book = lookupRemote(isbn);
		if (book != null) {
			log.info("lookedup remote and saved " + isbn);
			book = bookRepository.save(book);
		}
		System.err.println(book);
		return book;
	}

	public Book lookupRemote(String isbn) {
		Book book = null;

		try {
			
			RestTemplate restTemplate = new RestTemplate();
			
			String restURI = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
			ResponseEntity<String> json = restTemplate.getForEntity(restURI, String.class);
			if (json.getStatusCode() == HttpStatus.OK && checkIfAny(json.getBody())) {
				book = parseBook(json.getBody());
				book.setIsbn(isbn);
			} else {
				log.error(String.format("Book not found: %s, %s", json.getStatusCode(), json.toString()));
			}

		} catch (RestClientException rce) {
			log.error(String.format("Book not found: %s", isbn), rce);
		}

		return book;

	}

	public Book parseBook(String json) {
		Book book = new Book();
		book.setTitle(JsonPath.read(json, "$.items[0].volumeInfo.title"));

		JSONArray authors = JsonPath.read(json, "$.items[0].volumeInfo.authors");
		if (authors != null && authors.size() > 0) {
			StringBuilder authList = new StringBuilder();
			for (int i = 0; i < authors.size(); ++i) {
				authList.append(authors.get(i)).append(";");
			}
			book.setAuthor(authList.toString());
		}

		String year = JsonPath.read(json, "$.items[0].volumeInfo.publishedDate");
		if (year != null && year.length() >= 4) {
			book.setPublicationYear(Integer.parseInt(year.substring(0, 4)));
		} else {
			book.setPublicationYear(0);
		}

		book.setPublisher(JsonPath.read(json, "$.items[0].volumeInfo.publisher"));

		return book;
	}

	private boolean checkIfAny(String body) {
		Integer count = JsonPath.read(body, "$.totalItems");
		return count > 0;
	}

}
