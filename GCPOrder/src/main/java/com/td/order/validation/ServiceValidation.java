package com.td.order.validation;

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

	public Boolean verifyAccount(String accountID) {
		HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());
		ResponseEntity<String> response = restTemplate.exchange("http://ACCOUNT-SERVICE/account/verifyid/" + accountID,
				HttpMethod.POST, entity, String.class);
		if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().equals("true") )
			return true;
		else
			return false;
	}

	public Boolean verifyProduct(String productID) {
		HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());
		ResponseEntity<String> response = restTemplate.exchange("http://PRODUCT-SERVICE/product/verifyid/" + productID,
				HttpMethod.POST, entity, String.class);
		if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().equals("true"))
			return true;
		else
			return false;
	}

	public Boolean verfyProductList(String[] productList) {
		if (productList.length == 0)
			return false; 
		for (String productID : productList) {
			if (verifyProduct(productID) == false)
				return false;
		}
		return true;
	}

}
