package com.rrr.expense.manager.emapi.models;

public final class AccountBuilder {
    private Long id;
    private String title;
    private String description;

    private AccountBuilder() {
    }

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public AccountBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Account build() {
        Account account = new Account();
        account.setId(id);
        account.setTitle(title);
        account.setDescription(description);
        return account;
    }
}
