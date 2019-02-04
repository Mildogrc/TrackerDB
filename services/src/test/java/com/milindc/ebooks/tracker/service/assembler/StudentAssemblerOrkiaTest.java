package com.milindc.ebooks.tracker.service.assembler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.model.StudentView;

public class StudentAssemblerOrkiaTest {

	@Test
	public void test() {
		StudentAssembler studentAssemblerOkari = new StudentAssembler();
		Student s = new Student("m", "c", "", "s@g.com", "m", "4324", "3141", 17, 2202, null, "123");
		StudentView sV = new StudentView("m", "c", null, 123l, 17, 2020);
		assertEquals(studentAssemblerOkari.to(s).getFirstName(), sV.getFirstName());
		assertEquals(studentAssemblerOkari.to(sV).getFirstName(), s.getFirstName());
	}

}
