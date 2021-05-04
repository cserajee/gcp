package com.td.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.product.entity.Product;
import com.td.product.exception.InvalidDataException;
import com.td.product.exception.NotFoundException;
import com.td.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProduct() {
		List<Product> product = productRepository.findAll();
		if (product == null)
			throw new NotFoundException("No Data Found...");
		else
			return product;
	}

	@Override
	public Product getProduct(String id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent())
			return product.get();
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public Product saveProduct(Product product) {
		Product result = productRepository.save(product);
		if (result == null)
			throw new InvalidDataException("Invalid Data...");
		else
			return result;
	}

	@Override
	public Product updateProduct(Product product) {
		Optional<Product> productCheck = productRepository.findById(product.getId());
		if (productCheck.isPresent())
			return productRepository.save(product);
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public void deleteProduct(String id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent())
			productRepository.deleteById(id);
		else
			throw new InvalidDataException("Invalid ID...");
	}

	@Override
	public boolean verifyProduct(String id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent())
			return true;
		else
			return false;
	}

}
