package com.td.account.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class Account {

	@Id
	public String id;
  
	@Field(name = "first_name") 
	public String firstName;

	@Field(name = "last_name")
	public String lastName;
	
	@Field(name = "email") 
	public String email;

	@Field(name = "address") 
	public String address;

}
