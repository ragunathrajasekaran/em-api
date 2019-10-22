package com.rrr.expense.manager.emapi.services;

import com.rrr.expense.manager.emapi.models.Account;
import com.rrr.expense.manager.emapi.repositories.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Account> findAllAccounts(Pageable pageable) {
        return this.accountRepository.findAll(pageable);
    }

    @Override
    public Optional<Account> findAccountById(Long accountId) {
        return this.accountRepository.findById(accountId);
    }

    @Override
    public Account addAccount(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Account account) {
        this.accountRepository.delete(account);
    }
}
