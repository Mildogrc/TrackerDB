package com.milindc.ebooks.tracker;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.milindc.ebooks.tracker.service.CheckoutService;
import com.milindc.ebooks.tracker.service.StudentService;

@SpringBootApplication
@ComponentScan
@EntityScan("com.milindc.ebooks.tracker.db.model")
public class TrackerApplication {

	@Autowired
	CheckoutService cs;

	@Autowired
	StudentService ss;

	/*
	 * BitBucketID URL postgresql://root@Milinds-MBP:26257?sslmode=disable
	 */
	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);
	}

	@PostConstruct
	public void test() {
		Response rs = cs.getCheckout(1l);
		System.out.println(rs.getEntity());
		System.out.println(rs);
		System.out.println(ss.getStudentByStudentId("123"));
		
	}

}