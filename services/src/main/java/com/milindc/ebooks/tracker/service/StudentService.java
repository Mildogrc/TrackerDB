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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.db.StudentRepository;
import com.milindc.ebooks.tracker.db.model.Student;
import com.milindc.ebooks.tracker.service.assembler.StudentAssembler;
import com.milindc.ebooks.tracker.service.model.StudentView;
import com.milindc.ebooks.tracker.service.model.Students;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Path("/students")
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	StudentAssembler studentAssembler = new StudentAssembler();


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Students searchStudents(@QueryParam("lastName") String lastName, @QueryParam("firstName") String firstName,
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
		itr.stream().forEach(s -> log.debug(s.getFirstName()));
		students.setStudents(itr.stream().map(s -> studentAssembler.to(s)).collect(Collectors.toList()));
		log.debug(String.valueOf(students));
		return students;
	}


	@GET
	@Path("/{studentId}")
	public StudentView getStudentByStudentId(@PathParam("studentId") String studentId) {
		log.debug("Using Path Parameter Function for student ID");
		Student student = findStudentByStudentId(studentId);
		StudentView studentView = studentAssembler.to(student);
		return studentView;
	}

	public Student findStudentByStudentId(String studentId) {
		Student student = studentRepository.findByStudentId(studentId);
		return student;
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStudent(StudentView studentView, @Context HttpServletRequest request) {
		Student student = studentAssembler.to(studentView);
		try{
			student = studentRepository.save(student);
		} catch(DataAccessException e) {
			String msg = String.format("{\"error\": \"%s\"}", e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage());
			log.debug(msg);
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				return Response.status(Status.CONFLICT).entity(msg).build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity(msg).build();
			}
		}
		
		StudentView savedStudent = studentAssembler.to(student);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedStudent.getId())).build();
		return Response.created(uri).entity(savedStudent).build();
		
	}


}
