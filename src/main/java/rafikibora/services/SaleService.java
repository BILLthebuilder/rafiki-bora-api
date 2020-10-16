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
        //amount sent from pos
         Double amount = saleData.getAmountTransaction();

         //get merchant id using terminals table
         String merchantID = saleData.getTerminal().getMid().getMid();
         String customerPan = saleData.getCustomerPan(); // merchant's pan a/c if sale tx;
        try {
            Account sourceAccount = accountRepository.findByPan(customerPan);
            Account destAccount  = accountRepository.findByPan(merchantID);

            //System.out.println("============> src account Name: " + sourceAccount.getName());

            double customerAccBalance = sourceAccount.getBalance();

            if (customerAccBalance < amount) {
                throw new Exception("Insufficient funds");
            }

            //Debit customer account
            double newSourceAccBalance = customerAccBalance - amount;

            double merchantAccBalance = destAccount.getBalance();

            //Credit merchant account
            double newDestBalance = merchantAccBalance + amount;

            //Set updated account
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
