package com.td.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td.delivery.dto.MessageDTO;
import com.td.delivery.entity.Delivery;
import com.td.delivery.service.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	DeliveryService deliveryService;

	@GetMapping("")
	public List<Delivery> getAllDelivery() {
		return deliveryService.getAllDelivery();
	}

	@GetMapping("/{id}")
	public Delivery getDelivery(@PathVariable String id) {
		return deliveryService.getDelivery(id);
	}

	@PutMapping("")
	public Delivery updateDelivery(@RequestBody MessageDTO message) {
		return deliveryService.updateDelivery(message);
	}

}
