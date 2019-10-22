package com.rrr.expense.manager.emapi.services;

import com.rrr.expense.manager.emapi.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AccountService {
    Page<Account> findAllAccounts(Pageable pageable);
    Optional<Account> findAccountById(Long accountId);
    Account addAccount(Account account);
    void deleteAccount(Account account);
}
