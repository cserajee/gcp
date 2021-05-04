package com.td.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.td.order.config.GCPConfig;
import com.td.order.dto.MessageDTO;
import com.td.order.entity.OrderInfo;
import com.td.order.exception.InvalidDataException;
import com.td.order.exception.NotFoundException;
import com.td.order.repository.OrderRepository;
import com.td.order.validation.ServiceValidation;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ServiceValidation serviceValidation;
	@Autowired
	private GCPConfig.PubsubOutboundGateway messagingGateway;

	@Override
	public List<OrderInfo> getAllOrder(String accountID) {
		List<OrderInfo> order = orderRepository.findByaccountID(accountID);
		if (order == null)
			throw new NotFoundException("No Data Found...");
		else
			return order;
	}

	@Override
	public OrderInfo getOrder(String accountID, String orderID) {
		Optional<OrderInfo> order = orderRepository.findById(orderID);
		if (order.isPresent() && order.get().getAccountID().equals(accountID))
			return order.get();
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public OrderInfo saveOrder(String accountID, OrderInfo order) {
		if (serviceValidation.verifyAccount(order.getAccountID()) == false || !order.getAccountID().equals(accountID))
			throw new InvalidDataException("Invalid Account ID...");
		if (serviceValidation.verfyProductList(order.getProductID()) == false)
			throw new InvalidDataException("Invalid Product ID...");
		OrderInfo result = orderRepository.save(order);
		if (result == null)
			throw new InvalidDataException("Invalid Data...");
		else { 
			try {
				ObjectMapper Obj = new ObjectMapper();
				MessageDTO message = new MessageDTO(result.getId(), "order", result.isOrderStatus());
				String jsonStr = Obj.writeValueAsString(message);
				messagingGateway.sendToPubsub(jsonStr);
			} catch (Exception e) {
				throw new InvalidDataException("Please Try Again...");
			}
			return result;
		}
	}

	@Override
	public void deleteOrder(String accountID, String id) {
		Optional<OrderInfo> order = orderRepository.findById(id);
		if (order.isPresent() && order.get().getAccountID().equals(accountID))
			orderRepository.deleteById(id);
		else
			throw new InvalidDataException("Invalid Order ID...");
	}

	@Override
	public Boolean verifyOrder(String id) {
		Optional<OrderInfo> order = orderRepository.findById(id);
		if (order.isPresent())
			return true;
		else
			return false;
	}

	@Override
	public Boolean updatePackageStaus(String data) {
		try {
			ObjectMapper Obj = new ObjectMapper();
			MessageDTO message = Obj.readValue(data, MessageDTO.class);
			Optional<OrderInfo> orderCheck = orderRepository.findById(message.getId());
			if (orderCheck.isPresent()) { 
				OrderInfo order = orderCheck.get();
				if(order.isPackageStatus() != message.isStatus()) {
					order.setPackageStatus(message.isStatus());
					orderRepository.save(order);
				}
				return true;
			} 
			else
				return false; 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	@Override
	public Boolean updateDeliveryStaus(String data) {

		try {
			ObjectMapper Obj = new ObjectMapper();
			MessageDTO message = Obj.readValue(data, MessageDTO.class);
			Optional<OrderInfo> orderCheck = orderRepository.findById(message.getId());
			if (orderCheck.isPresent()) { 
				OrderInfo order = orderCheck.get();
				if(order.isDeliveryStatus() != message.isStatus()) {
					order.setDeliveryStatus(message.isStatus());
					orderRepository.save(order);
				}
				return true;
			} 
			else
				return false; 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
}
