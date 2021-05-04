package com.td.account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.td.account.entity.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

}
