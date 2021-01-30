package com.examle.springProject.repos;

import com.examle.springProject.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Long> {

    List<Account> findByName(String name);
}
