package rafikibora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.model.account.Account;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TransactionRepository;

@Service
public class DepositOrSaleService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public DepositOrSaleService() {
    }

    public void performDepositOrSale(Transaction depositSaleData) {
//        Double amount = depositSaleData.getAmountTransaction();
//        String sourceAccountNum = depositSaleData.getSourceAccountNumber();
//        String destAccountNum = depositSaleData.getDestinationAccountNumber(); // merchant's account a/c if sale tx;
//
//        try {
//            Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNum);
//            Account destAccount = accountRepository.findByAccountNumber(destAccountNum);
//
//            double sourceAccBalance = sourceAccount.getBalance();
//
//            if (sourceAccBalance < amount) {
//                throw new Exception("Insufficient funds");
//            }
//
//            double newSourceAccBalance = sourceAccBalance - amount;
//
//            double destAccBalance = destAccount.getBalance();
//            double newDestBalance = destAccBalance + amount;
//
//            sourceAccount.setBalance(newSourceAccBalance);
//            destAccount.setBalance(newDestBalance);
//
//            // persist changes to the database
//            accountRepository.save(sourceAccount);
//            accountRepository.save(destAccount);
//
//            // Add transaction to Transaction table
//            transactionRepository.save(depositSaleData);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

    }
}