package com.milindc.ebooks.tracker.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checkout")
public class Checkout extends AuditedEntity {

	/**
	 * This is usually given by the library
	 */
	@Column(unique = true)
	private String redemptionCode;
	
	private Student student;
	
	private Book book;
	
	private Date checkOutDate;
	
	private Date checkInDate;
	
}
