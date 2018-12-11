package com.milindc.ebooks.tracker.db.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Student extends Person {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// name
	// personal contact info
	private String phone1;

	private String phone2;

	private Integer age;

	private Integer academicYear;

	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BookCopy> borrowedBooks;
	

	public Student(String firstName, String lastName, String middleName, String email, String gender, String phone1, String phone2,
			Integer age, Integer academicYear, List<BookCopy> borrowedBooks) {
		super(firstName, lastName, middleName, email, gender);
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.age = age;
		this.academicYear = academicYear;
		this.borrowedBooks = borrowedBooks;
	}
}
