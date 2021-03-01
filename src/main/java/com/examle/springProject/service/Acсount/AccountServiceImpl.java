package com.examle.springProject.service.Acсount;

import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepo accountRepo;

    @Override
    public List<Account> findAllAccountsByOwner(User owner) {
        return accountRepo.findAccountsByOwner_id(owner.getId());
    }
    public Page<Account> findAllByOwner(User owner , Pageable pageable){
        return accountRepo.findAllByOwner_id(owner.getId() , pageable);
    }

    @Override
    public void createAccount(AccountDTO accountDTO , User owner) throws AccountAlreadyExistsException{
        if (accountExist(accountDTO.getNumber(), owner.getId())) {
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
        account.setCosts(account.getCosts()+costs);
        accountRepo.save(account);
    }
    @Override
    public Account findById(Long id){
        return accountRepo.findById(id).get();
    }

    @Override
    public void changePermission(Account account) {
        account.setBlocked(!account.isBlocked());
        accountRepo.save(account);
    }

    private boolean accountExist(String number ,Long owner_id){
        return accountRepo.findAccountsByOwner_idAndNumber(owner_id , number).size() != 0;}
}
