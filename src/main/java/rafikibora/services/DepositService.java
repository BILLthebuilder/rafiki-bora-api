package rafikibora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.dto.DepositDto;
import rafikibora.exceptions.AccountTransactionException;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TerminalRepository;
import rafikibora.repository.TransactionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class DepositService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TerminalRepository terminalRepository;


    public DepositService() {
    }

    public void performDeposit(DepositDto depositDto) throws Exception{
        String tempAmount = depositDto.getAmountTransaction();
        int len = tempAmount.length();
        Double amount = Double.parseDouble(tempAmount.substring(0,(len-2)));

        Optional<Account> optionalSrcAccount;
        Optional<Account> optionalDestAccount;
        Account sourceAccount = null;
        Account destAccount = null;
        String customerPan = depositDto.getCustomerPan();
        String merchantPan = depositDto.getMerchantPan();
        Optional<Terminal> optionalTerminal;
        Terminal terminal;

        optionalSrcAccount = accountRepository.findByPan(merchantPan);
        optionalDestAccount = accountRepository.findByPan(customerPan);
        if(optionalSrcAccount.isPresent())
            sourceAccount = optionalSrcAccount.get();
        if(optionalDestAccount.isPresent())
            destAccount = optionalDestAccount.get();

        double merchantAccBalance = sourceAccount.getBalance();

        if (merchantAccBalance < amount) {
            throw new Exception("Insufficient funds");
        }

        //debit merchant's account
        double newSourceAccBalance = merchantAccBalance - amount;
        // update merchant account
        sourceAccount.setBalance(newSourceAccBalance);

        double customerAccBalance = destAccount.getBalance();

        //credit customer's account
        double newDestBalance = customerAccBalance + amount;
        // update customer account
        destAccount.setBalance(newDestBalance);

        // persist changes to the database
        accountRepository.save(sourceAccount);
        accountRepository.save(destAccount);

        /** get terminal used in transaction */
        optionalTerminal = terminalRepository.findByTid(depositDto.getTerminal());
        if(optionalTerminal.isPresent())
            terminal = optionalTerminal.get();
        else
            throw new Exception("invalid terminal"); /** invalid merchant */

        // Add transaction to Transaction table
        Transaction newTransaction = new Transaction();
        newTransaction.setAmountTransaction(amount);
        newTransaction.setCurrencyCode("KES");
        newTransaction.setDateTimeTransmission(this.formatDateTime(depositDto.getDateTimeTransmission()));
        newTransaction.setTerminal(terminal);
        newTransaction.setRecipientEmail("merchant@email.com");
        newTransaction.setPan(merchantPan);
        newTransaction.setProcessingCode(depositDto.getProcessingCode());
        newTransaction.setSourceAccount(sourceAccount);
        newTransaction.setDestinationAccount(destAccount);
        newTransaction.setReferenceNo(depositDto.getTerminal()+System.currentTimeMillis());
        transactionRepository.save(newTransaction);
    }

    /**
     *
     * @param transmissionDateTime
     * @return formatted date
     * @throws AccountTransactionException
     */
    private Date formatDateTime(String transmissionDateTime) throws AccountTransactionException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String month = transmissionDateTime.substring(2,4);
        String day = transmissionDateTime.substring(4,6);
        String hour = transmissionDateTime.substring(6,8);
        String min = transmissionDateTime.substring(8);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String fullDateTime = year+"-"+month+"-"+day+" "+hour+":"+min+":00";
        SimpleDateFormat transmitDateTime = new SimpleDateFormat(pattern);
        Date date;
        try{
            date = transmitDateTime.parse(fullDateTime);
        }catch (ParseException ex){
            throw new AccountTransactionException("Failed to parse transaction date");
        }
        return date;
    }


//    public void performDeposit(Transaction transaction) {
//
//        //get amount from pos
//        Double amount = transaction.getAmountTransaction();
//        Optional<Account> optionalSrcAccount;
//        Optional<Account> optionalDestAccount;
//        Account sourceAccount = null;
//        Account destAccount = null;
//
//
//
//        //get merchant pan or customer pan from pos
//        String merchantPan = transaction.getMerchantPan();
//        String customerPan = transaction.getCustomerPan(); // customer's pan a/c;
//
//        try {
//            optionalSrcAccount = accountRepository.findByPan(merchantPan);
//            optionalDestAccount = accountRepository.findByPan(customerPan);
//            if(optionalSrcAccount.isPresent())
//                sourceAccount = optionalSrcAccount.get();
//
//            if(optionalDestAccount.isPresent())
//                destAccount = optionalDestAccount.get();
//
//            //System.out.println("============> src account Name: " + sourceAccount.getName());
//
//            double merchantAccBalance = sourceAccount.getBalance();
//
//            if (merchantAccBalance < amount) {
//                throw new Exception("Insufficient funds");
//            }
//
//            //debit merchant's account
//            double newSourceAccBalance = merchantAccBalance - amount;
//
//            double customerAccBalance = destAccount.getBalance();
//
//            //credit customer's account
//            double newDestBalance = customerAccBalance + amount;
//
//            sourceAccount.setBalance(newSourceAccBalance);
//            destAccount.setBalance(newDestBalance);
//
//            // persist changes to the database
//            accountRepository.save(sourceAccount);
//            accountRepository.save(destAccount);
//
//            // Add transaction to Transaction table
//            System.out.println("=================================> Deposit sale data: " + transaction);
//
//            transactionRepository.save(transaction);
//
//        } catch (Exception e) {
//            throw new TransactionDeniedException(e.getMessage());
//        }
//    }
}