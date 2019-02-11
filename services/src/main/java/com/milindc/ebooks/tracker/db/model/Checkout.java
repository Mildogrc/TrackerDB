package com.milindc.ebooks.tracker.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Checkout extends AuditedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5493948961685085718L;

	/**
	 * This is usually given by the library
	 */
	@Column(unique = true)
	private String redemptionCode;
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "bookId")
	private Book book;
	
	private Date checkOutDate;
	
	private Date checkInDate;
	
}
