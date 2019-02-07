package com.milindc.ebooks.tracker.service.assembler;

import com.milindc.ebooks.tracker.db.model.Checkout;
import com.milindc.ebooks.tracker.service.model.CheckoutView;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class CheckoutAssembler{
	
	static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	
	static {
		mapperFactory.classMap(Checkout.class, CheckoutView.class);
		mapperFactory.classMap(CheckoutView.class, Checkout.class);
	}
	
	public CheckoutView to(Checkout b) {
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(b, CheckoutView.class);
	}

	
	public Checkout to(CheckoutView bV) {
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(bV, Checkout.class);
	}
}