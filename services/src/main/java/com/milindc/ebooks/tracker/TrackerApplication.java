package com.milindc.ebooks.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EntityScan("com.milindc.ebooks.tracker.db.model")
public class TrackerApplication {
	/*
	 * BitBucketID URL
	 * postgresql://root@Milinds-MBP:26257?sslmode=disable
	 */
	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);
	}
}