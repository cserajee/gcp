package com.td.packing.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceValidation {
	@Autowired
	RestTemplate restTemplate;

	

	public Boolean verifyOrder(String orderID) { 
		HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());
		ResponseEntity<String> response = restTemplate.exchange("http://ORDER-SERVICE/order/verifyid/" + orderID,
				HttpMethod.POST, entity, String.class); 
		if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().equals("true"))
			return true;
		else
			return false;
	}

}
