package com.milindc.ebooks.tracker.service.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentView {
	
	private Long id; 
	
	private Long studentId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String phone1;
	
	private String phone2;
	
	private Integer enabled = 1;
	
	private Integer academicYear;
	
	private Integer age;
	
	@Getter(AccessLevel.NONE)
	private String fullName;

	public String getFullName() {
		return String.format("%s, %s %s", lastName, firstName, middleName);
	}

	public StudentView(String firstName, String lastName, String middleName, 
			Long id, Integer age, Integer academicYear) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.id = id;
		this.fullName = getFullName();
		this.academicYear = academicYear;
		this.age = age;
	}
	
}
