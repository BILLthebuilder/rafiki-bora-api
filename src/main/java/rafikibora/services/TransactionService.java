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

    //get transaction by id
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

    // get all transactions on  a specific account
    public List<Transaction> getTransactionsByPan(String pan){
        return repository.findByPan(pan);
    }

    // All transactions by type
    public List<Transaction> getTransactionsByProcessingCode(String type){
        return  repository.findByProcessingCode(type);
    }

    // All transactions by merchant
    public List<Transaction> getMerchantTransactions(String mid){
        return repository.merchantTransactions(mid);
    }

    // All transactions by terminal
    public List<Transaction> getTerminalTransactions(String tid){
        return repository.terminalTransactions(tid);
    }
}