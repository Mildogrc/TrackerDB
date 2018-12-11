package com.milindc.ebooks.tracker;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.service.BookService;
import com.milindc.ebooks.tracker.service.StudentService;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		registerEndpoints();
	}

	private void registerEndpoints() {
		register(StudentService.class);
		register(BookService.class);
	}
}
