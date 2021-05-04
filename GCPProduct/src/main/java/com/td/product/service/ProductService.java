package com.td.product.service;

import java.util.List;

import com.td.product.entity.Product;

public interface ProductService {

	public List<Product> getAllProduct();

	public Product getProduct(String id);

	public Product saveProduct(Product product);

	public Product updateProduct(Product product);

	public void deleteProduct(String id);

	public boolean verifyProduct(String id);

}
