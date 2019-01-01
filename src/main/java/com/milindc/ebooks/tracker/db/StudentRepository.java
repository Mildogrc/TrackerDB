package com.milindc.ebooks.tracker.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milindc.ebooks.tracker.db.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

	@Query("SELECT s FROM Student s where s.lastName = :lname")
	List<Student> findByLastName(String lname);
	
	@Query("SELECT s FROM Student s where s.firstName = :fname")
	List<Student> findByFirstName(String fname);

	@Query("SELECT s FROM Student s where s.phone1 = :phone or s.phone2 = :phone")
	List<Student> findByPhone(String phone);

	@Query("SELECT s FROM Student s where s.email = :email")
	List<Student> findByEMail(String email);


}