package com.rrr.expense.manager.emapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100, message = "Title length should be between 5 and 100")
    private String title;

    @NotNull
    @Size(min = 5, max = 255, message = "Description length should be between 5 and 255")
    private String description;

    public Account() {

    }

    public Account(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void mergeAccount(Account account) {
        this.setTitle(account.getTitle());
        this.setDescription(account.getDescription());
    }
}
