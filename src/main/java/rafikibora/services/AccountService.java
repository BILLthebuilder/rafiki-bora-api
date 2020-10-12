package rafikibora.services;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rafikibora.dto.Response;
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

    public ResponseEntity<?> getAccountById(int id) {
        Response response;
        Optional<Account> optional = repository.findById(id);
        Account account = null;
        if (optional.isPresent()) {
            account = optional.get();
        } else {
            response = new Response(Response.responseStatus.FAILED," Account not found for id :: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    public ResponseEntity<?> getAccountByName(String name) {
        Response response;
        Optional <Account> optional = repository.findByName(name);
        Account account = null;
        if (optional.isPresent()) {
            account = optional.get();
            //response = new Response(Response.responseStatus.SUCCESS,"Successful account");
        } else {
//            throw new RuntimeException(" Account not found for name :: " + name);
            response = new Response(Response.responseStatus.FAILED,"No account found for :: " + name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    public String deleteAccount(int id) {
        repository.deleteById(id);
        return "Account removed  !! " + id;
    }

    public Account updateAccount(Account accounts) {
        Account existingAccount = repository.findById(accounts.getId()).orElse(null);
        if (accounts.getName() != null) {
            existingAccount.setName(accounts.getName());
        }

        if ((Integer) accounts.getPan() != null) {
            existingAccount.setPan(accounts.getPan());
        }

        if ((Double) accounts.getBalance() != null) {
            existingAccount.setBalance(accounts.getBalance());
        }

        if (accounts.getAccountMakers() != null) {
            existingAccount.setAccountMakers(accounts.getAccountMakers());
        }

        return repository.save(existingAccount);
    }


}
