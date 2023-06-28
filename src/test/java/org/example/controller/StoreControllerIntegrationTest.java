package org.example.controller;

import org.example.repository.ToolRepository;
import org.example.service.CheckoutService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class StoreControllerIntegrationTest {

	@Autowired
	StoreController storeController;

	@MockBean
	CheckoutService checkoutService;

	@MockBean
	ToolRepository toolRepository;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * You named this, not me
	 * <p>
	 * JAKR - 9/3/15 - 5 days , 101 %
	 * <p>
	 * Expect validation error from 101 percent discount
	 */
	@Test
	public void test1() throws Exception {
		String user = "{\"toolCode\": \"JAKR\", \"rentalDays\" :5,\"checkoutDate\": \"09/03/2015\", \"discountPercent\": 101 }";
		mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.discountPercent", Is.is("Discount Cannot be more than 100")))
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * You named this, not me
	 * <p>
	 * JAKR - 9/3/15 - 5 days , 101 %
	 * <p>
	 * Expect validation error from 101 percent discount
	 */
	@Test
	public void verifyMinOneDay() throws Exception {
		String user = "{\"toolCode\": \"JAKR\", \"rentalDays\" :0,\"checkoutDate\": \"09/03/2015\", \"discountPercent\": 50 }";
		mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
						.content(user)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rentalDays", Is.is("Must rent for at least one Day")))
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON));
	}


}
