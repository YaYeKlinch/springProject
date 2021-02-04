package com.examle.springProject.repos;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.CardType;
import com.examle.springProject.domain.CreditCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreditCardRepo   extends CrudRepository<CreditCard, Long>
    {
        @Override
        Optional<CreditCard> findById(Long aLong);
        Optional<CreditCard> findFirstByOrderByNumberDesc();
        Optional<CreditCard> findByCardType(CardType cardType);
    }

