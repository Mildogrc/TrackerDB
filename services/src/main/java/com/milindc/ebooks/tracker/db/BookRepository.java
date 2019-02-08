package com.milindc.ebooks.tracker.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.db.model.Student;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, JpaSpecificationExecutor<Book>{
	@Query("SELECT b FROM Book b where b.title = :title")
	List<Book> findByTitle(String title);
	
	@Query("SELECT b FROM Book b where b.isbn = :isbn")
	Book findByIsbn(String isbn);
	
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
	
	static Specification<Book> matches(String author, String isbn, String title, String genre, String publicationYear,
			String editor, String publisher) {
		List<Specification<Book>> specs = new ArrayList<>();
		if(author != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("author"), author));
		}
		if(isbn != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("isbn"), author));
		}
		if(title != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("title"), title));
		}
		if(genre != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("genre"), genre));
		}
		if(publicationYear != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("publicationYear"), publicationYear));
		}
		if(editor != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("editor"), editor));
		}
		if(publisher != null) {
			specs.add((book, cq, cb) -> cb.equal(book.get("publisher"), publisher));
		}
		return collapse(specs);
	}

	static Specification<Book> collapse(List<Specification<Book>> listOfSpecs) {
		if(listOfSpecs.size() == 0) {
			return null;
		}
		Specification<Book> combinedSpec = listOfSpecs.get(0);
		for(int i = 1; i < listOfSpecs.size(); i++) {
			combinedSpec = combinedSpec.and(listOfSpecs.get(i));
		}
		
		return combinedSpec;
	}
	
}