package com.examle.springProject.controller.Dto;


import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountDTO {

    @NotBlank
    @Size(min=2, max=30)
    String name;

    @NotBlank
    @Size(min = 10 , max = 10)
    String number;

    public AccountDTO() {
    }

    public AccountDTO(@NotBlank @Size(min = 2, max = 30) String name, @NotBlank @Size(min = 10, max = 10) String number) {
        this.name = name;
        this.number = number;
    }
}
