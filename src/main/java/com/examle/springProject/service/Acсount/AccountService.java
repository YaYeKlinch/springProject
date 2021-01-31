package com.examle.springProject.service.Acсount;

import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;

import java.util.List;

public interface AccountService {

    List<Account> findAllAccountsByOwner(User owner);

    void createAccount(AccountDTO accountDTO , User owner) throws AccountAlreadyExistsException;
}
