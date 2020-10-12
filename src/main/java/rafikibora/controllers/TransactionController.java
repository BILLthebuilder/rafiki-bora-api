package rafikibora.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rafikibora.model.transactions.Transaction;
import rafikibora.services.TransactionService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PostMapping("/addTransaction")
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return service.saveTransaction(transaction);
    }


    @PostMapping("/addTransactions")
    public List<Transaction> addTransactions(@RequestBody List<Transaction> trac) { return service.saveTransactions(trac);}

    @GetMapping("/listTransactions")
    public List<Transaction> findAllTransactions() {
        return service.getTransactions();
    }

    @GetMapping("/transaction/{id}")
    public Transaction findTransactionById(@PathVariable int id) {
        return service.getTransactionById(id);
    }

    @GetMapping("/transactions/{resultCode}")
    public Transaction findTransactionByResultCode(@PathVariable @Valid String resultCode) {
        return service.getTransactionByName(resultCode);
    }

    @PutMapping("/updateTransaction")
    public Transaction updateAccount(@RequestBody Transaction transaction) {
        return service.updateTransaction(transaction);
    }

    @DeleteMapping("/deleteTransaction/{id}")
    public String deleteTransaction(@PathVariable int id) {
        return service.deleteTransaction(id);
    }
}
