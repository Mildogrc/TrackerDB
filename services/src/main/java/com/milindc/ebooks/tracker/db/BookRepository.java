package com.milindc.ebooks.tracker.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milindc.ebooks.tracker.db.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
	@Query("SELECT b FROM Book b where b.title = :title")
	List<Book> findByTitle(String title);
	
	@Query("SELECT b FROM Book b where b.isbn = :isbn")
	List<Book> findByIsbn(String isbn);
	
	@Query("SELECT b FROM Book b where b.author = :author")
	List<Book> findByAuthor(String author);
	
	@Query("SELECT b FROM Book b where b.publicationYear = :publicationYear")
	List<Book> findByPublicationYear(String publicationYear);
	
	@Query("SELECT b FROM Book b where b.editor = :editor")
	List<Book> findByEditor(String editor);
	
	@Query("SELECT b FROM Book b where b.publisher = :publisher")
	List<Book> findByPublisher(String publisher);
	
	@Query("SELECT b FROM Book b where b.genre = :genre")
	List<Book> findByGenre(String genre);
	
}