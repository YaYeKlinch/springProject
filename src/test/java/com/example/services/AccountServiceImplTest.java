package com.example.services;

import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.service.Acсount.AccountService;
import com.examle.springProject.service.Acсount.AccountServiceImpl;
import org.junit.Before;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testng.annotations.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
    Account account;

    @Mock
    Page<Account> accounts;

    User user = new User(1L);

    @Mock
    Sort sort;

    @InjectMocks
    AccountServiceImpl accountService = new AccountServiceImpl();

    AccountDTO accountDTO = new AccountDTO("testName" , "1234567891");

    @Before
    public void setUp(){
        initMocks(this);
        given(accountRepo.findAllByOwner_id(any(), any())).willReturn(accounts);
    }

    @Test
    void findAllByOwnerTest(){
        assertEquals(accounts , accountService.findAllByOwner(user , Optional.of(5), Optional.of(2) , sort));
        verify(accountRepo, times(1)).findAllByOwner_id(user.getId() , PageRequest.of(4,2, sort));
    }
}
