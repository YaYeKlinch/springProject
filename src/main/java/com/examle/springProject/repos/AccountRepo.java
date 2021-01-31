package com.examle.springProject.repos;

import com.examle.springProject.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends CrudRepository<Account, Long> {

    List<Account> findByName(String name);

    Optional<Account> findById(Long id);

    List<Account> findAccountsByOwner_id(@Param("owner_id") Long id);

    List<Account> findAccountsByOwner_idAndNumber(@Param("owner_id") Long id ,@Param("number") String number);
}
