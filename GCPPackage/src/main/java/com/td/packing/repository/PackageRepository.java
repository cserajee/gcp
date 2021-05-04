package com.td.packing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.td.packing.entity.Package;

@Repository
public interface PackageRepository extends MongoRepository<Package, String> {
	
	 

}
