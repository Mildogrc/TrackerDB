package com.milindc.ebooks.tracker.service.assembler;

import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.model.BookView;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class BookAssembler{
	
	static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	
	static {
		mapperFactory.classMap(Book.class, BookView.class);
		mapperFactory.classMap(BookView.class, Book.class);
	}
	
	public BookView to(Book b) {
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(b, BookView.class);
	}

	
	public Book to(BookView bV) {
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(bV, Book.class);
	}
}