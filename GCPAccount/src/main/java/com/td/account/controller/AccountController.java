package com.td.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.td.account.entity.Account;
import com.td.account.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService  accountService;

	@GetMapping("")
	public List<Account> getAllAccount() {
		return accountService.getAllAccount();
	}

	@GetMapping("/{id}")
	public Account getAccount(@PathVariable String id) {
		return accountService.getAccount(id);
	}

	@PostMapping("")
	public Account saveAccount(@RequestBody Account account) {
		return accountService.saveAccount(account);
	}

	@PutMapping("")
	public Account updateAccount(@RequestBody Account account) {
		return accountService.updateAccount(account);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable String id) {
		accountService.deleteAccount(id);
		return new ResponseEntity<>(id + " deleted..", HttpStatus.OK);
	}
	
	
	@PostMapping("/verifyid/{id}")
	public Boolean verifyAccount(@PathVariable String id) {
		
		if(accountService.verifyAccount(id))
			return true;
		else
			return false;
	}
	 
}
