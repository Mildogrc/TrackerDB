package com.milindc.ebooks.tracker.db.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Checkout {
	private Student student;
	
	private BookCopy bookCopy;
	
	private Date checkOutDate;
	
	private Date checkInDate;
	
}
