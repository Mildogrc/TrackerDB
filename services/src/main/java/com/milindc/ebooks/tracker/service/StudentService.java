package com.milindc.ebooks.tracker.service;

import static com.milindc.ebooks.tracker.db.StudentRepository.hasFirstName;
import static com.milindc.ebooks.tracker.db.StudentRepository.hasLastName;
import static com.milindc.ebooks.tracker.db.StudentRepository.hasPhone;
import static com.milindc.ebooks.tracker.db.StudentRepository.singleSpec;
import static org.springframework.data.jpa.domain.Specification.where;

import java.net.URI;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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
		students.setStudents(
				StreamSupport.stream(itr.spliterator(), false).map(b -> studentAssembler.to(b)).collect(Collectors.toList()));
		return students;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Students searchStudents(@QueryParam("lastName") String lastName,
									@QueryParam("firstName") String firstName,
									@QueryParam("phone") String phone) {
		
		
		List<Specification<Student>> predicates = new ArrayList<>();
		
	    if (lastName != null) {
	        predicates.add(hasLastName(lastName));
	    }
	    if (firstName != null) {
	        predicates.add(hasFirstName(firstName));
	    }
	    if (phone != null) {
	        predicates.add(hasPhone(phone));
	    }
	 
		
		Students students = new Students();
		List<Student> itr = studentRepository.findAll(where(singleSpec(predicates)));
		itr.stream().forEach(s -> System.out.println(s.getFirstName()));
		students.setStudents(itr.stream().map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		System.out.println(students);
		return students;
	}

	@GET
	@Path("/lastName/{lastName}")
	public Students getStudentsByLastName(@PathParam("lastName") String lastName) {
		Students students = new Students();
		List<Student> itr = studentRepository.findByLastName(lastName);
		students.setStudents(
				StreamSupport.stream(itr.spliterator(), false).map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		System.out.println("Using Path Parameter Funtion for lastName");
		return students;
	}

	@GET
	@Path("/firstName/{firstName}")
	public Students getStudentsByFirstName(@PathParam("firstName") String firstName) {
		Students students = new Students();
		List<Student> itr = studentRepository.findByFirstName(firstName);

		students.setStudents(
				StreamSupport.stream(itr.spliterator(), false).map(s -> studentAssembler.to(s)).collect(Collectors.toList()));System.out.println("Using Path Parameter Funtion for fisrtName");
		return students;
	}

	@GET
	@Path("/phone/{phone}")
	public Students getStudentsByPhoneNumber(@PathParam("phone") String phone) {
		Students students = new Students();
		List<Student> itr = studentRepository.findByPhone(phone);

		students.setStudents(
				StreamSupport.stream(itr.spliterator(), false).map(s -> studentAssembler.to(s)).collect(Collectors.toList()));System.out.println("Using Path Parameter Funtion for phone");
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
