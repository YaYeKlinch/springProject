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

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {
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
