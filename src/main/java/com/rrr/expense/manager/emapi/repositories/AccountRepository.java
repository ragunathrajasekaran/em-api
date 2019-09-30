package com.rrr.expense.manager.emapi.repositories;

import com.rrr.expense.manager.emapi.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
