package com.milindc.ebooks.tracker.service.assembler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.model.BookView;

public class BookAssemblerTest {

	@Test
	public void test() {
		Book b = new Book("23812903","title", "author", 2002, "editor", "publisher" ,"genre",null);
		BookView bV = new BookView(null, "title", "23812903","author", 2002, "editor", "publisher", "genre");
		
		BookAssembler a = new BookAssembler();
		assertEquals(b.getIsbn(), a.to(bV).getIsbn());
		assertEquals(bV.getIsbn(), a.to(b).getIsbn());
	}

}
