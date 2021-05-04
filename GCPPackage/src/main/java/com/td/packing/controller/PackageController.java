package com.td.packing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td.packing.dto.MessageDTO;
import com.td.packing.entity.Package;
import com.td.packing.service.PackageService;

@RestController
@RequestMapping("/package")
public class PackageController {

	@Autowired
	PackageService packageService;

	@GetMapping("")
	public List<Package> getAllPackage() {
		return packageService.getAllPackage();
	}

	@GetMapping("/{id}")
	public Package getPackage(@PathVariable String id) {
		return packageService.getPackage(id);
	}

	@PutMapping("")
	public Package updatePackage(@RequestBody MessageDTO message) {
		return packageService.updatePackage(message);
	}

}
