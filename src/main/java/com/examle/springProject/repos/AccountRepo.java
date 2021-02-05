package com.examle.springProject.repos;

import com.examle.springProject.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends PagingAndSortingRepository<Account, Long> {

    Page<Account> findAllByOwner_id(@Param("owner_id") Long id , Pageable pageable);
    Optional<Account> findById(Long id);

    List<Account> findAccountsByOwner_id(@Param("owner_id") Long id);

    List<Account> findAccountsByOwner_idAndNumber(@Param("owner_id") Long id ,@Param("number") String number);
}
