package rafikibora.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import rafikibora.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rafikibora.services.AccountService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping
    public ResponseEntity<?> addAccount(@Valid @RequestBody Account account) {

        Account newAccount = service.saveAccount(account);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newEmployeeURI = ServletUriComponentsBuilder.fromCurrentRequest() // get the URI for this request
                .path("/{id}") // add to it a path variable
                .buildAndExpand(newAccount.getId()) // populate that path variable with the newly created restaurant id
                .toUri(); // convert that work into a human readable URI
        responseHeaders.setLocation(newEmployeeURI); // in the header, set the location location to that URI

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }


    @GetMapping
    public List<Account> findAllAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable int id) {
        return (ResponseEntity<Account>) service.getAccountById(id);
    }

    @GetMapping("accounts/{name}")
    public ResponseEntity<Account> findAccountByName(@PathVariable @Valid String name) {
        return (ResponseEntity<Account>) service.getAccountByName(name);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateAccount(@RequestBody Account account, @PathVariable int id) {
        service.updateAccount(account, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable @Param("id") int id) {
        return service.deleteAccount(id);

    }
}
