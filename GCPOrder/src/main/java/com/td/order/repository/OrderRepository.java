package com.td.order.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.td.order.entity.OrderInfo;

@Repository
public interface OrderRepository extends MongoRepository<OrderInfo, String> {
	
	List<OrderInfo> findByaccountID(String accountID);

}
