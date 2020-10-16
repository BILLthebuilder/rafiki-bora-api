package rafikibora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.exceptions.TransactionDeniedException;
import rafikibora.model.account.Account;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TransactionRepository;


@Service
public class SaleService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public SaleService() {
    }

    public void performSale(Transaction saleData) {
        Double amount = saleData.getAmountTransaction();
        String merchantID = saleData.getTerminal().getMid().getMid();
        String customerPan = saleData.getCustomerPan(); // merchant's pan a/c if sale tx;
        try {
            Account sourceAccount = accountRepository.findBycustomerPan(customerPan);
            Account destAccount  = accountRepository.findBymerchantID(merchantID);

            //System.out.println("============> src account Name: " + sourceAccount.getName());

            double customerAccBalance = sourceAccount.getBalance();

            if (customerAccBalance < amount) {
                throw new Exception("Insufficient funds");
            }

            double newSourceAccBalance = customerAccBalance - amount;

            double merchantAccBalance = destAccount.getBalance();
            double newDestBalance = merchantAccBalance + amount;

            sourceAccount.setBalance(newSourceAccBalance);
            destAccount.setBalance(newDestBalance);

            // persist changes to the database
            accountRepository.save(sourceAccount);
            accountRepository.save(destAccount);

            // Add transaction to Transaction table
            System.out.println("=================================> Deposit sale data: " + saleData);

            transactionRepository.save(saleData);

        } catch (Exception e) {
            throw new TransactionDeniedException(e.getMessage());
        }
    }
}
