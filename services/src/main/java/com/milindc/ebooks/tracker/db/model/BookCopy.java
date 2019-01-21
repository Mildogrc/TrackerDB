package com.milindc.ebooks.tracker.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
@Entity
public class BookCopy extends AuditedEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is usually given by the library
	 */
	@Column(unique = true)
	private String redemptionCode;
	
	@ManyToOne
	private Book book;
	
	private Double price;
	
	@ManyToOne
	private Student student;
}