package com.td.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td.order.entity.OrderInfo;
import com.td.order.service.OrderServiceImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderServiceImpl orderService;

	@GetMapping("/{accountID}")
	public List<OrderInfo> getAllOrder(@PathVariable String accountID) {
		return orderService.getAllOrder(accountID);
	}

	@GetMapping("/{accountID}/{orderID}")
	public OrderInfo getOrder(@PathVariable String accountID, @PathVariable String orderID) {
		return orderService.getOrder(accountID, orderID);
	}

	@PostMapping("/{accountID}")
	public OrderInfo saveOrder(@PathVariable String accountID, @RequestBody OrderInfo order) {
		return orderService.saveOrder(accountID, order);
	}

	@DeleteMapping("/{accountID}/{orderID}")
	public ResponseEntity<?> deleteOrder(@PathVariable String accountID, @PathVariable String orderID) {
		orderService.deleteOrder(accountID, orderID);
		return new ResponseEntity<>(orderID + " deleted..", HttpStatus.OK);
	}

	@PostMapping("/verifyid/{id}")
	public Boolean verifyOrder(@PathVariable String id) {

		if (orderService.verifyOrder(id))
			return true;
		else
			return false;
	}

}
