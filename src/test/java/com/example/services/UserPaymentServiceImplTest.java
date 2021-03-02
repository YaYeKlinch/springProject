package com.example.services;

import com.examle.springProject.domain.User;
import com.examle.springProject.domain.UserPayment;
import com.examle.springProject.repos.UserPaymentRepo;
import com.examle.springProject.service.UserPaymentService.UserPaymentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserPaymentServiceImplTest {
    @Mock
    UserPaymentRepo userPaymentRepo;

    @Mock
    Page<UserPayment> userPayments;

    @InjectMocks
    UserPaymentServiceImpl userPaymentService = new UserPaymentServiceImpl();


    User user = new User(1L);
    Sort sort;
    @Before
    public void setUp(){
        initMocks(this);
        given(userPaymentRepo.findAllByUser_id(any(), any())).willReturn(userPayments);
    }

    @Test
    public void findAllByUserTest(){
        assertEquals(userPayments, userPaymentService.findAllByUser(user.getId() , Optional.of(5), Optional.of(2) , sort));
        verify(userPaymentRepo , times(1)).findAllByUser_id(user.getId(), PageRequest.of(4,2));
    }

}
