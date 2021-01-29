package com.examle.springProject.service;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.User;

import java.util.Optional;

public interface UserService {
     Optional<User> registerUser(UserDTO userDto) throws UserAlreadyExistException;
}
