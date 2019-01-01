package com.milindc.ebooks.tracker.service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.milindc.ebooks.tracker.db.StudentRepository;
import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.assembler.StudentAssembler;
import com.milindc.ebooks.tracker.service.model.StudentView;
import com.milindc.ebooks.tracker.service.model.Students;

@Component
@Path("/students")
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	StudentAssembler studentAssembler = new StudentAssembler();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudents() {
		Students students = new Students();
		Iterable<Student> itr = studentRepository.findAll();

		students.setStudents(StreamSupport.stream(itr.spliterator(), false).map(s -> studentAssembler.to(s))
				.collect(Collectors.toList()));
		return students;
	}
	
	@GET
	public Students searchStudents(@RequestParam("lastName") String lastName,
									@RequestParam("lastName") String firstName,
									@RequestParam("lastName") String phone) {
		
		
		
		Students students = new Students();
		// find the right way using https://www.baeldung.com/spring-data-criteria-queries
		//	students.setStudents(itr.stream().map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		return students;
	}


	@GET
	@Path("/lastName/{lastName}")
	public Students getStudentsByLastName(@PathParam("lastName") String lastName) {
		Students students = new Students();
		List<Student> itr = studentRepository.findByLastName(lastName);

		students.setStudents(itr.stream().map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		return students;
	}

	@GET
	@Path("/firstName/{firstName}")
	public Students getStudentsByFirstName(@PathParam("firstName") String firstName) {
		Students students = new Students();
		List<Student> itr = studentRepository.findByFirstName(firstName);

		students.setStudents(itr.stream().map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		return students;
	}

	@GET
	@Path("/phone/{phone}")
	public Students getStudentsByPhoneNumber(@PathParam("phone") String phone) {
		Students students = new Students();
		List<Student> itr = studentRepository.findByPhone(phone);

		students.setStudents(itr.stream().map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		return students;
	}

	@POST
	@Consumes("application/json") // MediaType.APPLICATION_JSON)
	public Response createStudent(StudentView studentView, @Context HttpServletRequest request) {
		Student student = studentAssembler.to(studentView);
		student = studentRepository.save(student);
		System.out.println(student);
		StudentView savedStudent = studentAssembler.to(student);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedStudent.getId()))
				.build();
		return Response.created(uri).entity(savedStudent).build();
	}

//	@PUT
//	@Consumes("application/json")
//		public Response updateStudent(Student student, @Context HttpServletRequest request) {
//		
//	}		

//	@DELETE
//	@Consumes("application/json")
//	public void deleteByLastName(@PathParam("lastName") Student lastName) {
//		StudentService sS = new StudentService();
//		sS.deleteByLastName(lastName);
//	}
}
