package com.milindc.ebooks.tracker.service.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutView {
	
	private String redemptionCode;
	
	private StudentView student;
	
	private BookView book;
	
	private Date checkOutDate;
	
	private Date checkInDate;
	
	
}
