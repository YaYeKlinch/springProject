package com.examle.springProject.controller.Dto;

import com.examle.springProject.controller.utility.EmailValidation;
import com.examle.springProject.controller.utility.PasswordMatches;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@PasswordMatches
public class UserDTO {
    @NotEmpty
    @Size(min=2, max=30)
    private String firstName;

    @NotBlank
    @Size(min=2, max=30)
    private String lastName;

    @NotBlank
    @Size(min=2, max=30)
    private String password;
    private String matchingPassword;

    @NotBlank
    @EmailValidation
    private String email;

    public UserDTO() {
    }

    public UserDTO(@NotEmpty @Size(min = 2, max = 30) String firstName, @NotBlank @Size(min = 2, max = 30) String lastName, @NotBlank @Size(min = 2, max = 30) String password, String matchingPassword, @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }
}
