package com.milindc.ebooks.tracker.service.assembler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.model.StudentView;

public class StudentAssemblerOrkiaTest {

	@Test
	public void test() throws JsonProcessingException {
		StudentAssembler studentAssemblerOkari = new StudentAssembler();
		Student s = new Student("Mil", "c", "", "s@g.com", "m", "4324", "3141", 17, 2202, null, "123");
		StudentView sV = new StudentView(null, 123l, "Mil", "Cha", null, "123123123", "12312311", null, 2020, 17, null);
		assertEquals(studentAssemblerOkari.to(s).getFirstName(), sV.getFirstName());
		assertEquals(studentAssemblerOkari.to(sV).getFirstName(), s.getFirstName());
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(sV);
		System.out.println(json);
	}

}
