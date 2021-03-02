package com.examle.springProject.service.Acсount;

import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.AccountAlreadyExistsException;
import com.examle.springProject.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountRepo accountRepo;

    public Page<Account> findAllByOwner(User owner , Optional<Integer> page, Optional<Integer> size, Sort sort ){
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        logger.debug("Trying to find all accounts from user with id:"
                + owner.getId() + "on page" + currentPage + "with size of page" + sizeOfPage);
        if(sort==null){
            pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        }
        else {
            pageRequest = PageRequest.of(currentPage - 1 , sizeOfPage , sort);
        }
        return accountRepo.findAllByOwner_id(owner.getId() , pageRequest);
    }

    @Override
    public void createAccount(AccountDTO accountDTO , User owner) throws AccountAlreadyExistsException {
        logger.info("Trying to create account with number: "
                + accountDTO.getNumber() +" of user with id:" + owner.getId());
        if (accountExist(accountDTO.getNumber(), owner)) {
            logger.info("tried to create account with existing number"
                    + accountDTO.getNumber() + "for user with id " + owner.getId() +",throwing AccountAlreadyExistsException");
            throw new AccountAlreadyExistsException(
                    "There is an account with that number: "
                            +  accountDTO.getNumber());
        }
        Account accountToCreate = new Account();
        accountToCreate.setBlocked(false);
        accountToCreate.setCosts(0);
        accountToCreate.setName(accountDTO.getName());
        accountToCreate.setNumber(accountDTO.getNumber());
        accountToCreate.setOwner(owner);
        accountRepo.save(accountToCreate);
    }
    @Override
    public void increaseCosts( Account account, int costs) {
        logger.info("trying to increase costs on" + costs +" of account with number" + account.getNumber());
        account.setCosts(account.getCosts()+costs);
        accountRepo.save(account);
    }

    @Override
    public void changePermission(Account account) {
        logger.info("trying to change permission for account with number "+ account.getNumber());
        account.setBlocked(!account.isBlocked());
        accountRepo.save(account);
    }

    private boolean accountExist(String number ,User owner) {
        logger.info("trying to find account with number " + number + "of user with id " + owner.getId());
        return owner.getAccounts().stream().anyMatch(account -> account.getNumber().equals(number));
    }
}
