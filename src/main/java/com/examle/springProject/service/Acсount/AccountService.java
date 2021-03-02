package com.examle.springProject.service.Acсount;

import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.AccountAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void createAccount(AccountDTO accountDTO , User owner) throws AccountAlreadyExistsException;
    void increaseCosts(Account account, int costs);
    void changePermission(Account account);
    Page<Account>  findAllByOwner(User owner ,Optional<Integer> page,Optional<Integer> size, Sort sort );


}
