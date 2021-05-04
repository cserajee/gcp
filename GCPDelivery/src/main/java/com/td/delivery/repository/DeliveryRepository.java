package com.td.delivery.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.td.delivery.entity.Delivery;

@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {  
	public Delivery findByOrderID(String orderID);
}
