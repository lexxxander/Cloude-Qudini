package com.qudini.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qudini.bean.Customer;

@RestController
public class CustomerController {

	@RequestMapping("/customer/sortjsonobj")
	public Customer[] sortJsonObj(@RequestBody List<Customer> customers) {
		System.out.println("SORT() customers..." + customers);
		if (customers == null) {
			return null;
		}
		Collections.sort(customers, new Comparator<Customer>() {
			public int compare(Customer c1, Customer c2) {
				return c1.getDuetime().compareTo(c2.getDuetime());
			}
		});
		return (Customer[]) customers.stream().toArray(Customer[]::new);
	}

	@RequestMapping("/customer/sortjsonstr")
	public Customer[] sortJsonStr(@RequestBody String customersJson)
			throws JsonParseException, JsonMappingException, IOException {
		System.out.println("SORTJSON() customers..." + customersJson);

		if (customersJson == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		List<Customer> customers = mapper.readValue(customersJson,
				TypeFactory.defaultInstance().constructCollectionType(List.class, Customer.class));

		Collections.sort(customers, new Comparator<Customer>() {
			public int compare(Customer c1, Customer c2) {
				return c1.getDuetime().compareTo(c2.getDuetime());
			}
		});
		return (Customer[]) customers.stream().toArray(Customer[]::new);

		// return null;
	}
}
