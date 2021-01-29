package com.examle.springProject.controller.Dto;

import com.examle.springProject.controller.utility.EmailValidation;
import com.examle.springProject.controller.utility.PasswordMatches;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@PasswordMatches
public class UserDTO {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    @EmailValidation
    private String email;

}
