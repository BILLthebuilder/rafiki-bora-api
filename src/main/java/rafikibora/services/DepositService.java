package rafikibora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.exceptions.TransactionDeniedException;
import rafikibora.model.account.Account;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TransactionRepository;

import java.util.Optional;

@Service
public class DepositService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public DepositService() {
    }

    public void performDeposit(Transaction depositData) {

        //get amount from pos
        Double amount = depositData.getAmountTransaction();
        Optional<Account> optionalSrcAccount;
        Optional<Account> optionalDestAccount;
        Account sourceAccount = null;
        Account destAccount = null;



        //get merchant pan or customer pan from pos
        String merchantPan = depositData.getMerchantPan();
        String customerPan = depositData.getCustomerPan(); // customer's pan a/c;

        try {
            optionalSrcAccount = accountRepository.findByPan(merchantPan);
            optionalDestAccount = accountRepository.findByPan(customerPan);
            if(optionalSrcAccount.isPresent())
                sourceAccount = optionalSrcAccount.get();

            if(optionalDestAccount.isPresent())
                destAccount = optionalDestAccount.get();

            //System.out.println("============> src account Name: " + sourceAccount.getName());

            double merchantAccBalance = sourceAccount.getBalance();

            if (merchantAccBalance < amount) {
                throw new Exception("Insufficient funds");
            }

            //debit merchant's account
            double newSourceAccBalance = merchantAccBalance - amount;

            double customerAccBalance = destAccount.getBalance();

            //credit customer's account
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