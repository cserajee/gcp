package com.td.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.account.entity.Account;
import com.td.account.exception.InvalidDataException;
import com.td.account.exception.NotFoundException;
import com.td.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Account> getAllAccount() {
		List<Account> account = accountRepository.findAll();
		if (account == null)
			throw new NotFoundException("No Data Found...");
		else
			return account;
	}

	@Override
	public Account getAccount(String id) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent())
			return account.get();
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public Account saveAccount(Account account) {
		Account result = accountRepository.save(account);
		if (result == null)
			throw new InvalidDataException("Invalid Data...");
		else
			return result;
	}

	@Override
	public Account updateAccount(Account account) {
		Optional<Account> accountCheck = accountRepository.findById(account.getId());
		if (accountCheck.isPresent())
			return accountRepository.save(account);
		else
			throw new NotFoundException("No Data Found...");
	}

	@Override
	public void deleteAccount(String id) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent())
			accountRepository.deleteById(id);
		else
			throw new InvalidDataException("Invalid Account ID...");
	}

	@Override
	public Boolean verifyAccount(String id) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent())
			return true;
		else
			return false;
	}

}
