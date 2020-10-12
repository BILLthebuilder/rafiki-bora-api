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
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/addAccount")
   public Account addAccount(@RequestBody Account accounts) {
        return service.saveAccount(accounts);
    }


    @PostMapping("/addAccounts")
    public List<Account> addAccounts(@RequestBody List<Account> acc) { return service.saveAccounts(acc);}

    @GetMapping("/listAccounts")
    public List<Account> findAllAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable int id) {
        return service.getAccountById(id);
    }

    @GetMapping("/accounts/{name}")
    public ResponseEntity<Account> findAccountByName(@PathVariable @Valid String name) {
        return service.getAccountByName(name);
    }

    @PutMapping("/updateAccount")
    public Account updateAccount(@RequestBody Account accounts) {
        return service.updateAccount(accounts);
    }

    @DeleteMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable int id) {
        return service.deleteAccount(id);
    }
}
