package com.td.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {

	@Id
	public String id;
  
	@Field(name = "product_name")
	@NonNull
	public String productName;

	@Field(name = "product_description")
	public String description;

	@Field(name = "available")
	@NonNull
	public int available;

}
