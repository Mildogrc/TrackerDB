package com.milindc.ebooks.tracker.resource;


import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
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

import com.milindc.ebooks.tracker.db.model.Checkout;
import com.milindc.ebooks.tracker.service.CheckoutService;
import com.milindc.ebooks.tracker.service.assembler.CheckoutAssembler;
import com.milindc.ebooks.tracker.service.model.BookView;
import com.milindc.ebooks.tracker.service.model.CheckoutView;
import com.milindc.ebooks.tracker.service.model.Checkouts;

import lombok.extern.slf4j.Slf4j;

@Component
@Path("/checkouts")
@Slf4j
public class CheckoutResource {
	@Autowired
	private CheckoutService checkoutService;

	CheckoutAssembler checkoutAssembler = new CheckoutAssembler();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCheckouts() {
		Checkouts checkouts = new Checkouts();
		Iterable<Checkout> itr = checkoutService.getCheckouts();
		checkouts.setCheckouts(StreamSupport.stream(itr.spliterator(), false).map(b -> checkoutAssembler.to(b)).collect(Collectors.toList()));
		log.debug("Find All books works");
		return Response.ok(checkouts).build();
	}

	@POST
	@Consumes("application/json")//MediaType.APPLICATION_JSON)
	public Response checkout(CheckoutView checkoutView, @Context HttpServletRequest request) {
		Checkout checkout = checkoutAssembler.to(checkoutView);
		checkout = checkoutService.createCheckout(checkout);
		log.debug(String.format("Retrieved checkout %s", checkout));
		CheckoutView savedCheckout = checkoutAssembler.to(checkout);
		URI uri = UriBuilder.fromUri(request.getRequestURL().toString()).path(String.valueOf(savedCheckout.getRedemptionCode())).build();
		return Response.created(uri).entity(savedCheckout).build();
	}
}
