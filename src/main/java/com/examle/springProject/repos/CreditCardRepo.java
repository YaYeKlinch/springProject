package com.examle.springProject.repos;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.CreditCard;
import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepo   extends CrudRepository<CreditCard, Long>
    {

    }

