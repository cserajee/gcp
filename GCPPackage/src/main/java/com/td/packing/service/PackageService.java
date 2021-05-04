package com.td.packing.service;

import java.util.List;

import com.td.packing.dto.MessageDTO;
import com.td.packing.entity.Package;

public interface PackageService {

	public List<Package> getAllPackage();

	public Package getPackage(String id);

	public Boolean savePackage(String accountID);

	public Package updatePackage(MessageDTO msg);

	public void deletePackage(String id);
}
