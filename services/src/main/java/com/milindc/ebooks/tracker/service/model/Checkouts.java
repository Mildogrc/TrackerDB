package com.milindc.ebooks.tracker.service.model;

import java.util.List;

import lombok.Data;

@Data
public class Checkouts {
	private List<CheckoutView> checkouts;
}
