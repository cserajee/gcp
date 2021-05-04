package com.td.delivery.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.td.delivery.config.GCPConfig;
import com.td.delivery.dto.MessageDTO;
import com.td.delivery.entity.Delivery;
import com.td.delivery.exception.InvalidDataException;
import com.td.delivery.exception.NotFoundException;
import com.td.delivery.repository.DeliveryRepository;
import com.td.delivery.validation.ServiceValidation; 

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveryRepository deliveryRepository;
	@Autowired
	ServiceValidation serviceValidation;
	@Autowired
	private GCPConfig.PubsubOutboundGateway messagingGateway;
	
	@Override
	public List<Delivery> getAllDelivery() {
		List<Delivery> pack = deliveryRepository.findAll();
		if (pack == null)
			throw new NotFoundException("No Data Found...");
		else
			return pack;
	}

	@Override
	public Delivery getDelivery(String id) {
		Optional<Delivery> pack = deliveryRepository.findById(id);
		if (pack.isPresent())
			return pack.get();
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public Boolean saveDelivery(String data) {
		
		try {
			ObjectMapper Obj = new ObjectMapper();
			MessageDTO message = Obj.readValue(data, MessageDTO.class);
			if (serviceValidation.verifyOrder(message.getId()) == false)
				return false; 
			Delivery delivery = new Delivery(); 
			Delivery delCheck = deliveryRepository.findByOrderID(message.getId());
			if(delCheck!=null) {
				delivery.setId(delCheck.getId());
			} 
			delivery.setOrderID(message.getId()); 
			delivery.setDeliveryStatus(false);
			delivery.setDeliveryDate(new Date());
			Delivery result = deliveryRepository.save(delivery);
			if (result == null)
				return false;
			else
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return false; 
		 
	}

	@Override
	public Delivery updateDelivery(MessageDTO msg) {
		Optional<Delivery> delCheck = deliveryRepository.findById(msg.getId());
		if (delCheck.isPresent()) {
			Delivery delivery = delCheck.get();
			delivery.setDeliveryStatus(msg.isStatus());
			delivery.setDeliveryDate(new Date());
			delivery = deliveryRepository.save(delivery);
			try {
				ObjectMapper Obj = new ObjectMapper();
				msg.setId(delivery.getOrderID());
				msg.setName("delivery");
				String jsonStr = Obj.writeValueAsString(msg);
				messagingGateway.sendToPubsub(jsonStr);
				return delivery;
			} catch (Exception e) {
				throw new InvalidDataException("Please Try Again...");
			} 
		} else
			throw new NotFoundException("No Data Found...");
		
		 
	}

	@Override
	public void deleteDelivery(String id) {
		Optional<Delivery> pack = deliveryRepository.findById(id);
		if (pack.isPresent())
			deliveryRepository.deleteById(id);
		else
			throw new InvalidDataException("Invalid Package ID...");
	}
	
	
}
