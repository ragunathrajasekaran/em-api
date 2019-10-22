package com.rrr.expense.manager.emapi.controllers;

import com.rrr.expense.manager.emapi.models.Account;
import com.rrr.expense.manager.emapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<Page<Account>> accounts(Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(this.accountService.findAllAccounts(pageable));
    }

    @GetMapping(value = "/accounts/{accountId}")
    private ResponseEntity<Account> accountById(@PathVariable Long accountId) {
        return this.accountService
                .findAccountById(accountId)
                .map(ResponseEntity.accepted()::body)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/accounts")
    private ResponseEntity<Account> addAccount(@Valid @RequestBody Account account) {
        return ResponseEntity
                .ok()
                .body(this.accountService.addAccount(account));
    }

    @PutMapping(value = "/accounts/{accountId}")
    private ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long accountId) {
        return this.accountService
                .findAccountById(accountId)
                .map(accountFound -> {
                    accountFound.mergeAccount(account);
                    return ResponseEntity
                            .accepted()
                            .body(this.accountService.addAccount(account));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/accounts/{accountId}")
    private ResponseEntity deleteAccount(@PathVariable Long accountId) {
        return this.accountService
                .findAccountById(accountId)
                .map(accountFound -> {
                    this.accountService.deleteAccount(accountFound);
                    return ResponseEntity
                            .accepted()
                            .build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
