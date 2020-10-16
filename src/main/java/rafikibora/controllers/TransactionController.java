package rafikibora.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rafikibora.model.transactions.Transaction;
import rafikibora.services.TransactionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return service.saveTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> findAllTransactions() {
        return service.getTransactions();
    }

    @GetMapping("/{id}")
    public Transaction findTransactionById(@PathVariable int id) {
        return service.getTransactionById(id);
    }


    @PatchMapping
    public Transaction updateAccount(@RequestBody Transaction transaction) {
        return service.updateTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable int id) {
        return service.deleteTransaction(id);
    }
}
