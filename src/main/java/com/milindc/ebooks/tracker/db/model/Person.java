package com.milindc.ebooks.tracker.db.model;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class Person extends AuditedEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String firstName;
	
	protected String lastName;
	
	protected String middleName;
	
	protected String email;
	
	protected String gender;
	
}