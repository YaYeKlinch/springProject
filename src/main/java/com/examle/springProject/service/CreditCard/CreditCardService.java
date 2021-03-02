package com.examle.springProject.service.CreditCard;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.CardType;
import com.examle.springProject.exceptions.TypeCardException;

public interface CreditCardService {
    void createAndSaveCard(Account account , int pin , CardType type) throws TypeCardException;
}
