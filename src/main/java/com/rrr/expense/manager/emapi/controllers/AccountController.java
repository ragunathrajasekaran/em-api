package com.rrr.expense.manager.emapi.controllers;

import com.rrr.expense.manager.emapi.models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    private List<Account> accounts = new ArrayList<>();

    @GetMapping(value = "/accounts")
    private ResponseEntity<List<Account>> accounts() {
        return ResponseEntity
                .ok()
                .body(this.accounts);
    }

    @GetMapping(value = "/accounts/{accountId}")
    private ResponseEntity<Account> accountById(@PathVariable Long accountId) {
        return this
                .accounts
                .stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .map(account -> ResponseEntity.ok().body(account))
                .orElseThrow(
                        () -> new RuntimeException("Account Id " + accountId + " Is Not Found")
                );
    }

    @PostMapping(value = "/accounts")
    private ResponseEntity<Account> addAccount(@Valid @RequestBody Account account) {
        this.accounts.add(account);
        return ResponseEntity
                .ok()
                .body(account);
    }

    @PutMapping(value = "/accounts/{accountId}")
    private ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long accountId) {
        return this
                .accounts
                .stream()
                .filter(acc -> acc.getId().equals(accountId))
                .findFirst()
                .map(acc -> {
                    acc.setTitle(account.getTitle());
                    acc.setDescription(account.getDescription());
                    return ResponseEntity
                            .ok()
                            .body(acc);
                })
                .orElseThrow(
                        () -> new RuntimeException("Account Id " + accountId + " Is Not Found")
                );
    }

    @DeleteMapping(value = "/accounts/{accountId}")
    private ResponseEntity deleteAccount(@PathVariable Long accountId) {
        return this
                .accounts
                .stream()
                .filter(acc -> acc.getId().equals(accountId))
                .findFirst()
                .map(acc -> {
                    this.accounts.remove(acc);
                    return ResponseEntity
                            .ok()
                            .build();
                })
                .orElseThrow(
                        () -> new RuntimeException("Account Id " + accountId + " Is Not Found")
                );
    }
}
