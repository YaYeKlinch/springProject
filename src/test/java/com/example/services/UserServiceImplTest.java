package com.example.services;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.Role;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.UserAlreadyExistException;
import com.examle.springProject.repos.UserRepo;
import com.examle.springProject.service.User.UserServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {
    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl();

    @Mock
    User userEx;

    UserDTO userDTO = new UserDTO("TestName" , "TestSurname" ,
            "testPass" , "testPass" , "test@test.t");
    String existingEmail = "existTest@test";
    UserDTO userDTOEx = new UserDTO("TestName" , "TestSurname" ,
            "testPass" , "testPass" , existingEmail);



    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp(){
        initMocks(this);
        given(userRepo.findByUsername(existingEmail)).willReturn(userEx);
    }
    @Test
    public void registerUserTest(){
        userService.registerUser(userDTO);
        User user = new User("TestName" , "TestSurname" ,"test@test.t" ,"testPass" , true, Collections.singleton(Role.USER));
        verify(userRepo ,times(1)).save(user);
    }
    @Test
    public void registerUserThrowsUserAlreadyExistsExceptionTest(){
        exceptionRule.expect(UserAlreadyExistException.class);
        userService.registerUser(userDTOEx);

    }
}
