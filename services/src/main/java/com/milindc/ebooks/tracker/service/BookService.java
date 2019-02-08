package com.milindc.ebooks.tracker.service;

import static com.milindc.ebooks.tracker.db.BookRepository.matches;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.milindc.ebooks.tracker.db.BookRepository;
import com.milindc.ebooks.tracker.db.model.Book;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;


@Slf4j
@Component
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Iterable<Book> getBooks() {
		Iterable<Book> books = bookRepository.findAll();

		log.debug("Find All books works");
		return books;
	}


	public Book findBookByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

	
	public Book saveBookByIsbn(String isbn) {
		
		Book book = bookRepository.findByIsbn(isbn);

		if(book == null) {
			book = lookupRemote(isbn);
			if(book != null) {
				log.info("lookedup remote and saved " + isbn);
				book = createBook(book);
			}
		}
		
		return book;
	}
		
	public Book createBook(Book book) {
		book = bookRepository.save(book);
		log.debug(String.format("Retrieved book %s", book));
		return book;
	}

	public List<Book> getBooks(String author, String isbn, String title, String genre, String publicationYear,
			String editor, String publisher) {

		Specification<Book> spec = matches(author, isbn, title, genre, publicationYear, editor, publisher);
		
		return bookRepository.findAll(where(spec));
	}

	protected Book lookupRemote(String isbn) {
		Book book = null;
		
		RestTemplate restTemplate = new RestTemplate();
		
		String restURI = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
		ResponseEntity<String> json =  restTemplate.getForEntity(restURI, String.class);
		if(json.getStatusCode() == HttpStatus.OK && checkIfAny(json.getBody())) {
			book = parseBook(json.getBody());
		} else {
			log.error(String.format("Book not found: %s, %s", json.getStatusCode(), json.toString()));
		}
		
		return book;
		
	}

	protected Book parseBook(String json) {
		Book book = new Book();
		book.setTitle(JsonPath.read(json, "$.items[0].volumeInfo.title"));

		JSONArray authors = JsonPath.read(json, "$.items[0].volumeInfo.authors");
		if(authors != null && authors.size() > 0) {
			StringBuilder authList = new StringBuilder();
			for(int i = 0; i < authors.size(); ++ i) {
				authList.append(authors.get(i)).append(";");
			}
			book.setAuthor(authList.toString());
		}
		
		String year = JsonPath.read(json, "$.items[0].volumeInfo.publishedDate");
		if(year != null && year.length() >= 4) {
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
