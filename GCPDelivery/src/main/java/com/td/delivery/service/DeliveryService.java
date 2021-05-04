package com.td.delivery.service;

import java.util.List;

import com.td.delivery.dto.MessageDTO;
import com.td.delivery.entity.Delivery;

public interface DeliveryService {

	public List<Delivery> getAllDelivery();

	public Delivery getDelivery(String id);

	public Boolean saveDelivery(String orderID);

	public Delivery updateDelivery(MessageDTO msg);

	public void deleteDelivery(String id);
}
