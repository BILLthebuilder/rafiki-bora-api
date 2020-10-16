package rafikibora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.exceptions.TransactionDeniedException;
import rafikibora.model.account.Account;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TransactionRepository;

@Service
public class DepositService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public DepositService() {
    }

    public void performDeposit(Transaction depositData) {
        Double amount = depositData.getAmountTransaction();
        String merchantPan = depositData.getMerchantPan();
        String customerPan = depositData.getCustomerPan(); // customer's pan a/c;

        try {
            Account sourceAccount = accountRepository.findBymerchantPan(merchantPan);
            Account destAccount = accountRepository.findBycustomerPan(customerPan);

            //System.out.println("============> src account Name: " + sourceAccount.getName());

            double merchantAccBalance = sourceAccount.getBalance();

            if (merchantAccBalance < amount) {
                throw new Exception("Insufficient funds");
            }

            double newSourceAccBalance = merchantAccBalance - amount;

            double customerAccBalance = destAccount.getBalance();
            double newDestBalance = customerAccBalance + amount;

            sourceAccount.setBalance(newSourceAccBalance);
            destAccount.setBalance(newDestBalance);

            // persist changes to the database
            accountRepository.save(sourceAccount);
            accountRepository.save(destAccount);

            // Add transaction to Transaction table
            System.out.println("=================================> Deposit sale data: " + depositData);

            transactionRepository.save(depositData);

        } catch (Exception e) {
            throw new TransactionDeniedException(e.getMessage());
        }
    }
}