package com.milindc.ebooks.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.BookRepository;
import com.milindc.ebooks.tracker.db.model.Book;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public Iterable<Book> getBooks() {
		Iterable<Book> books = bookRepository.findAll();

		log.debug("Find All books works");
		return books;
	}

	public List<Book> getBookByAuthor(String author) {
		List<Book> books = bookRepository.findByAuthor(author);
		log.debug("Find by author works");
		return books;
	}
	
	public List<Book> getBookByIsbn(String isbn) {
		List<Book> books = bookRepository.findByIsbn(isbn);

		log.debug("Find by isbn works");
		return books;
	}
	
	public List<Book> getBookByTitle(String title) {
		List<Book> books = bookRepository.findByTitle(title);
		log.debug("Find by title works");
		return books;
	}
	
	public List<Book> getBookByGenre(String genre) {
		List<Book> books = bookRepository.findByGenre(genre);
		log.debug("Find by author genre");
		return books;
	}
	
	public List<Book> getBookBypublicationYear(String publicationYear) {
		List<Book> books = bookRepository.findByPublicationYear(publicationYear);
		return books;
	}
	
	public List<Book> getBookByEditor(String editor) {
		List<Book> books = bookRepository.findByEditor(editor);
		return books;
	}
	
	public List<Book> getBookByPublisher(String publisher) {
		List<Book> books = bookRepository.findByPublisher(publisher);

		return books;
	}
	
	public Book createBook(Book book) {
		book = bookRepository.save(book);
		log.debug(String.format("Retrieved book %s", book));
		return book;
	}

	public Iterable<Book> getBooks(String author, String isbn, String title, String genre, String publicationYear,
			String editor, String publisher) {
		return null;
	}
}
