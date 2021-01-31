package com.examle.springProject.service.Acсount;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepo accountRepo;

    @Override
    public List<Account> findAllAccountsByOwner(User owner) {
        return accountRepo.findAccountsByOwner_id(owner.getId());
    }
}
