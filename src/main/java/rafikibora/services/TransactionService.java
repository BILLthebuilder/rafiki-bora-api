package rafikibora.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public Transaction saveTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> saveTransactions(List<Transaction> transaction) {
        return repository.saveAll(transaction);
    }

    public List<Transaction> getTransactions() {
        return repository.findAll();
    }

    public Transaction getTransactionById(int id) {

        Optional<Transaction> optional = repository.findById(id);
        Transaction transaction = null;
        if (optional.isPresent()) {
            transaction = optional.get();
        } else {
            throw new RuntimeException(" Transaction not found for id :: " + id);
        }
        return transaction;
    }

    public String deleteTransaction(int id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

    public Transaction updateTransaction(Transaction transaction) {
        Transaction existingTransaction = repository.findById(transaction.getId()).orElse(null);
        existingTransaction.setAmountTransaction(transaction.getAmountTransaction());
        existingTransaction.setCurrencyCode(transaction.getCurrencyCode());
        existingTransaction.setProcessingCode(transaction.getProcessingCode());
        existingTransaction.setSourceAccount(transaction.getSourceAccount());
        return repository.save(existingTransaction);
    }


}