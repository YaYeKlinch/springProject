package com.examle.springProject.repos;

import com.examle.springProject.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Long> {

    List<Account> findByName(String name);

    List<Account> findAccountsByOwner_id(@Param("owner_id") Long id);
}
