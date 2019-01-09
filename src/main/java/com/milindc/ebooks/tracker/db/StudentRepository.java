package com.milindc.ebooks.tracker.db;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.milindc.ebooks.tracker.db.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

	@Query("SELECT s FROM Student s where s.lastName = :lname")
	List<Student> findByLastName(String lname);

	@Query("SELECT s FROM Student s where s.firstName = :fname")
	List<Student> findByFirstName(String fname);

	@Query("SELECT s FROM Student s where s.phone1 = :phone or s.phone2 = :phone")
	List<Student> findByPhone(String phone);

	@Query("SELECT s FROM Student s where s.email = :email")
	List<Student> findByEMail(String email);

	static Specification<Student> hasFirstName(String firstName) {
		return (student, cq, cb) -> cb.equal(student.get("firstName"), firstName);
	}

	static Specification<Student> hasLastName(String lastName) {
		return (student, cq, cb) -> cb.equal(student.get("lastName"), lastName);
	}

	static Specification<Student> hasPhone(String phone) {
		return (student, cq, cb) -> cb.equal(student.get("phone1"), phone);
	}

	static Specification<Student> singleSpec(List<Specification<Student>> listOfSpecs){
		Specification<Student> combinedSpec = listOfSpecs.get(0);
		for(int i = 1; i < listOfSpecs.size(); i++) {
			combinedSpec = combinedSpec.and(listOfSpecs.get(i));
		}
		return combinedSpec;
	}
}