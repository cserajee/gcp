package com.td.packing.entity;

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
@Document(collection = "package")
public class Package {

	@Id
	public String id;

	@Field(name = "order_id")  
	public String orderID;

	@Field(name = "package_date")  
	public Date packageDate;
 
	@Field(name = "package_status")
	public boolean packageStatus;
	 
}
