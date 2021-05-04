package com.td.order.service;

import java.util.List;

import com.td.order.entity.OrderInfo;

public interface OrderService {
	
	public List<OrderInfo> getAllOrder(String accountID);

	public OrderInfo getOrder(String accountID, String orderID);

	public OrderInfo saveOrder(String accountID, OrderInfo order);

	public void deleteOrder(String accountID, String id);

	public Boolean verifyOrder(String id);

	public Boolean updatePackageStaus(String data);
	
	public Boolean updateDeliveryStaus(String data);
}
