package com.td.packing.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.td.packing.config.GCPConfig;
import com.td.packing.dto.MessageDTO;
import com.td.packing.entity.Package;
import com.td.packing.exception.InvalidDataException;
import com.td.packing.exception.NotFoundException;
import com.td.packing.repository.PackageRepository;
import com.td.packing.validation.ServiceValidation;

@Service
public class PackageServiceImpl implements PackageService {

	@Autowired
	PackageRepository packageRepository;
	@Autowired
	ServiceValidation serviceValidation;
	@Autowired
	private GCPConfig.PubsubOutboundGateway messagingGateway;

	@Override
	public List<Package> getAllPackage() {
		List<Package> pack = packageRepository.findAll();
		if (pack == null)
			throw new NotFoundException("No Data Found...");
		else
			return pack;
	}

	@Override
	public Package getPackage(String id) {
		Optional<Package> pack = packageRepository.findById(id);
		if (pack.isPresent())
			return pack.get();
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public Boolean savePackage(String data) {

		try {
			ObjectMapper Obj = new ObjectMapper();
			MessageDTO message = Obj.readValue(data, MessageDTO.class);
			if (serviceValidation.verifyOrder(message.getId()) == false)
				return false;
			Package pack = new Package();
			pack.setOrderID(message.getId());
			pack.setPackageStatus(false);
			pack.setPackageDate(new Date());
			Package result = packageRepository.save(pack);
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
	public Package updatePackage(MessageDTO msg) { 
		Optional<Package> packCheck = packageRepository.findById(msg.getId()); 
		if (packCheck.isPresent()) {
			Package pack = packCheck.get();
			pack.setPackageStatus(msg.isStatus());
			pack.setPackageDate(new Date());
			pack = packageRepository.save(pack);
			try {
				ObjectMapper Obj = new ObjectMapper();
				msg.setId(pack.getOrderID());
				msg.setName("package");
				String jsonStr = Obj.writeValueAsString(msg);
				messagingGateway.sendToPubsub(jsonStr);
				return pack;
			} catch (Exception e) {
				throw new InvalidDataException("Please Try Again...");
			} 
		} else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public void deletePackage(String id) {
		Optional<Package> pack = packageRepository.findById(id);
		if (pack.isPresent())
			packageRepository.deleteById(id);
		else
			throw new InvalidDataException("Invalid Package ID...");
	}

}
