package com.milindc.ebooks.tracker.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book extends AuditedEntity {
	private static final long serialVersionUID = 1L;

	private String isbn;

	private String title;
	
	private String author;
	
	private Integer publicationYear;
	
	private String editor;
	
	private String publisher;
	
	private String genre;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Checkout> copies = new ArrayList<>(); 
	
}