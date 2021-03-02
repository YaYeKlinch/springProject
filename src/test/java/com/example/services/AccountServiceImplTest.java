package com.example.services;

import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.service.Acсount.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceImplTest {

    @Mock
    AccountRepo accountRepo;

    @Mock
    Page<Account> accounts;

    @InjectMocks
    AccountServiceImpl accountService = new AccountServiceImpl();

    AccountDTO accountDTO = new AccountDTO("testName" , "1234567891");
    User user = new User(1L);
    Sort sort;

    @Before
    public void setUp(){
        initMocks(this);
        given(accountRepo.findAllByOwner_id(any(), any())).willReturn(accounts);
    }

    @Test
    public void findAllByOwnerTest(){
        assertEquals(accounts , accountService.findAllByOwner(user , Optional.of(5), Optional.of(2) , sort));
        verify(accountRepo, times(1)).findAllByOwner_id(user.getId() , PageRequest.of(4,2));
    }
    @Test
    public void createAccountTest(){
        user.setAccounts(new HashSet<>());
        accountService.createAccount(accountDTO , user);
        Account account = new Account(1L , accountDTO.getName(),accountDTO.getNumber() , false , 0 , user);
        verify(accountRepo,times(1)).save(account);
    }

    @Test
    public void increaseCosTsTest(){
        Account account = new Account();
        account.setCosts(0);
        accountService.increaseCosts(account, 1);
        assertEquals(account.getCosts(), 1);
        verify(accountRepo,times(1)).save(account);
    }
    @Test
    public void changePermissionTest(){
        Account account = new Account();
        account.setBlocked(true);
        accountService.changePermission(account);
        assertFalse(account.isBlocked());
        verify(accountRepo,times(1)).save(account);
    }

}
