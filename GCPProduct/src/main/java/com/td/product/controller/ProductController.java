package com.td.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td.product.entity.Product;
import com.td.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("")
	public List<Product> getAllProduct() {
		return productService.getAllProduct();
	}

	@GetMapping("/{id}")
	public Product getProduct(@PathVariable String id) {
		return productService.getProduct(id);
	}

	@PostMapping("")
	public Product saveProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@PutMapping("")
	public Product updateProduct(@RequestBody Product product) {
		return productService.updateProduct(product);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(id + " deleted..", HttpStatus.OK);
	}
	
	@PostMapping("/verifyid/{id}")
	public Boolean verifyProduct(@PathVariable String id) { 
		if(productService.verifyProduct(id))
			return true;
		else
			return false;
	}

}
