package com.td.order.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_info")
public class OrderInfo {

	@Id
	public String id;

	@Field(name = "account_id")  
	public String accountID;

	@Field(name = "order_date") 
	public Date orderDate;

	@Field(name = "order_status")
	public boolean orderStatus;
	
	@Field(name = "package_status")
	public boolean packageStatus;
	
	@Field(name = "delivery_status")
	public boolean deliveryStatus;

	@Field(name = "product_id")
	public String[] productID;
	 
}
