package com.milindc.ebooks.tracker;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.stereotype.Component;

import com.milindc.ebooks.tracker.service.BookService;
import com.milindc.ebooks.tracker.service.CheckoutService;
import com.milindc.ebooks.tracker.service.StudentService;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(JerseyProperties jerseyProperties) {
		registerEndpoints();
	}

	private void registerEndpoints() {
		register(StudentService.class);
		register(BookService.class);
		register(CheckoutService.class);
	}

}
