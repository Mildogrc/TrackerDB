package com.milindc.ebooks.tracker.service.assembler;

import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.model.StudentView;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class StudentAssembler{
	
	static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	
	static {
		mapperFactory.classMap(Student.class, StudentView.class);
		mapperFactory.classMap(StudentView.class, Student.class);
	}

	public StudentView to(Student s) {
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(s, StudentView.class);
	}

	
	public Student to(StudentView sV) {
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(sV, Student.class);
	}

}
