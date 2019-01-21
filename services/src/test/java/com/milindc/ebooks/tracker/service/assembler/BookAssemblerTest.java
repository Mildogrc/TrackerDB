package com.milindc.ebooks.tracker.service.assembler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.model.BookView;

public class BookAssemblerTest {

	@Test
	public void test() {
		Book b = new Book(null, "g", 23812903L,"m", 2002,null,null,null,null);
	//	Book b = new Book(authorID, title, isbn, author, publicationYear, editor, publisher, genre, copies)
		BookView bV = new BookView(null, "g", 23812903L,null,null,null,null,null);
		
		BookAssembler a = new BookAssembler();
		assertEquals(b.getIsbn(), a.to(bV).getIsbn());
		assertEquals(bV.getIsbn(), a.to(b).getIsbn());
	}

}
