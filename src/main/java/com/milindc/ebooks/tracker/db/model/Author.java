package com.milindc.ebooks.tracker.db.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
@Entity
public class Author extends Person{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int firstPublicationYear;
	private Integer lastPublicationYear;
}
