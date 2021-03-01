package com.examle.springProject.service.User;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.UserAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface UserService {
     Optional<User> registerUser(UserDTO userDto) throws UserAlreadyExistException;
     List<User> findAll();
     void update(User user);
}
