package com.milindc.ebooks.tracker.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookView {
	
	private Long id;
	
	private String title;
	
	private String isbn;
	
	private String author;
	
	private Integer publicationYear;
	
	private String editor;
	
	private String publisher;
	
	private String genre;
	
}
