package rafikibora.services;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rafikibora.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account saveAccount(Account accounts) {
        return repository.save(accounts);
    }

    public List<Account> saveAccounts(List<Account> accounts) {
        return repository.saveAll(accounts);
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public ResponseEntity<Account> getAccountById(int id) {

        Optional<Account> optional = repository.findById(id);
        Account account = null;
        if (optional.isPresent()) {
            account = optional.get();
        } else {
            throw new RuntimeException(" Account not found for id :: " + id);
        }
        return ResponseEntity.ok().body(account);
    }

    public ResponseEntity<Account> getAccountByName(String name) {
        Optional <Account> optional = repository.findByName(name);
        Account account = null;
        if (optional.isPresent()) {
            account = optional.get();
        } else {
            throw new RuntimeException(" Account not found for name :: " + name);
        }
        return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(account);
    }

    public String deleteAccount(int id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

    public Account updateAccount(Account accounts) {
        Account existingAccount = repository.findById(accounts.getId()).orElse(null);
        existingAccount.setName(accounts.getName());
        existingAccount.setPan(accounts.getPan());
        existingAccount.setBalance(accounts.getBalance());
        existingAccount.setAccountMakers(accounts.getAccountMakers());
        return repository.save(existingAccount);
    }


}
