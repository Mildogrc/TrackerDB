package com.milindc.ebooks.tracker.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milindc.ebooks.tracker.db.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

	@Query("SELECT b FROM Book b where b.isbn = :isbn")
	Book findByIsbn(String isbn);
	
	
}