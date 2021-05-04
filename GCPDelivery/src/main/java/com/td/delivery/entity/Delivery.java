package com.td.delivery.entity;

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
@Document(collection = "delivery")
public class Delivery {

	@Id
	public String id;

	@Field(name = "order_id")  
	public String orderID;

	@Field(name = "delivery_date") 
	public Date deliveryDate; 
	
	@Field(name = "delivery_status")
	public boolean deliveryStatus;
 
	 
}
