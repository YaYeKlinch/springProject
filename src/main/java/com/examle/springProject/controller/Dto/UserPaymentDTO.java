package com.examle.springProject.controller.Dto;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class UserPaymentDTO {

    private Long number;
    private int pin;
    private int cost;
}
