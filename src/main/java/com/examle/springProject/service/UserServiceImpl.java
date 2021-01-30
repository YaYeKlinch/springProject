package com.examle.springProject.service;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.Role;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<User> registerUser(UserDTO userDto) throws UserAlreadyExistException{
            if (emailExists(userDto.getEmail())) {
                throw new UserAlreadyExistException(
                        "There is an account with that email address: "
                                +  userDto.getEmail());
            }
        Set<ConstraintViolation<UserDTO>> violations =      validator.validate(userDto);
        User userToCreate = new User();
        userToCreate.setFirsName(userDto.getFirstName());
        userToCreate.setLastName(userDto.getLastName());
        userToCreate.setActive(true);
        userToCreate.setPassword(userDto.getPassword());
        userToCreate.setUsername(userDto.getEmail());
        userToCreate.setRoles(Collections.singleton(Role.USER));
        return Optional.ofNullable(userRepo.save(userToCreate));
    }
    private boolean emailExists(String email){
        return userRepo.findByUsername(email) != null;
    }

}
