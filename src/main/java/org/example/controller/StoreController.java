package org.example.controller;

import org.example.model.Checkout;
import org.example.model.RentalAgreement;
import org.example.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

@RestController
public class StoreController {

	private final CheckoutService checkoutService;

	@Autowired
	public StoreController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	@PostMapping(value = "/checkout",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public RentalAgreement checkout(@RequestBody Checkout checkout) {
		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);
		rentalAgreement.toConsole();
		return rentalAgreement;
	}


}