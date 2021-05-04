package com.td.account.service;

import java.util.List;

import com.td.account.entity.Account;

public interface AccountService {
	public List<Account> getAllAccount();

	public Account getAccount(String id);

	public Account saveAccount(Account account);

	public Account updateAccount(Account account);

	public void deleteAccount(String id);

	public Boolean verifyAccount(String id);
}
