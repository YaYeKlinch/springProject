package com.examle.springProject.service.User;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.Role;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.UserRepo;
import com.examle.springProject.exceptions.UserAlreadyExistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("trying to find user with username " + username);
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public void registerUser(UserDTO userDto) throws UserAlreadyExistException {
        logger.info("trying to register user with username " + userDto.getEmail());
            if (emailExists(userDto.getEmail())) {
                logger.info("tried to register user with username " + userDto.getEmail() +
                        ", throwing UserAlreadyExistException");
                throw new UserAlreadyExistException(
                        "There is an account with that email address: "
                                +  userDto.getEmail());
            }
        validator.validate(userDto);
        User userToCreate = new User();
        userToCreate.setFirsName(userDto.getFirstName());
        userToCreate.setLastName(userDto.getLastName());
        userToCreate.setActive(true);
        userToCreate.setPassword(userDto.getPassword());
        userToCreate.setUsername(userDto.getEmail());
        userToCreate.setRoles(Collections.singleton(Role.USER));
        userRepo.save(userToCreate);
    }

    @Override
    public List<User> findAll() {
        logger.info("trying to find all users");
        return userRepo.findAll();
    }

    @Override
    public void update(User user) {
        logger.info("trying to update user with id " + user.getId());
        userRepo.save(user);
    }

    private boolean emailExists(String email){
        logger.info("trying to find user with email" +  email);
        return userRepo.findByUsername(email) != null;
    }

}
