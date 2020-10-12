package rafikibora.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import rafikibora.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rafikibora.services.AccountService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping
   public Account addAccount(@RequestBody Account account) {
        return service.saveAccount(account);
    }


    @GetMapping
    public List<Account> findAllAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable int id) {
        return (ResponseEntity<Account>) service.getAccountById(id);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Account> findAccountByName(@PathVariable @Valid String name) {
        return (ResponseEntity<Account>) service.getAccountByName(name);
    }

    @PatchMapping("/")
    public Account updateAccount(@RequestBody Account account) {
        return service.updateAccount(account);
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable int id) {
        return service.deleteAccount(id);
    }
}
