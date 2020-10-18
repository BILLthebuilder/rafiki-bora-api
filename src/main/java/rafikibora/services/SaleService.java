package rafikibora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.dto.SaleDto;
import rafikibora.exceptions.AccountTransactionException;
import rafikibora.exceptions.TransactionDeniedException;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.transactions.Transaction;
import rafikibora.model.users.User;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TerminalRepository;
import rafikibora.repository.TransactionRepository;
import rafikibora.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class SaleService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    public SaleService() {
    }

    public void performSale(SaleDto saleDto) {

        Optional<Account> optionalSrcAccount;
        Account sourceAccount = null;
        Account destAccount = null;
        String customerPan = saleDto.getPan();
        Optional<Terminal> optionalTerminal;
        Terminal terminal;
        Optional<User> optionalMerchant;
        User merchant;
        double merchantAccBalance;
        double newmerchantAccBalance;

        String tempAmount = saleDto.getAmountTransaction();
        int len = tempAmount.length();
        Double amount = Double.parseDouble(tempAmount.substring(0,(len-2)));

        try {
            // get merchant by mid
            optionalMerchant = userRepository.findByMid(saleDto.getMerchant());
            if(optionalMerchant.isPresent()) {
                merchant= optionalMerchant.get();

                // get merchant account
                destAccount = merchant.getUserAccount();

                // get merchant account balance
                merchantAccBalance = destAccount.getBalance();

                //Credit merchant account and update balance
                newmerchantAccBalance = merchantAccBalance + amount;
                destAccount.setBalance(newmerchantAccBalance);
                accountRepository.save(destAccount);
            }
            else{
                throw new Exception("invalid merchant"); /** invalid merchant */
            }

            // get customer account by pan
            optionalSrcAccount = accountRepository.findByPan(customerPan);
            if(optionalSrcAccount.isPresent()){
                sourceAccount = optionalSrcAccount.get();
                // get customer balance
                double customerAccBalance = sourceAccount.getBalance();

                System.out.println("******customer balance************");
                System.out.println(customerAccBalance);
                System.out.println("******customer balance************");

                if (customerAccBalance >= amount) {
                    //Debit customer account and update account
                    double newSourceAccBalance = customerAccBalance - amount;
                    sourceAccount.setBalance(newSourceAccBalance);
                    accountRepository.save(sourceAccount);
                }else{
                    throw new Exception("Insufficient funds");
                }

            }else{
                throw new Exception("No matching account");
            }


            // get terminal used in transaction
            optionalTerminal = terminalRepository.findByTid(saleDto.getTerminal());
            if(optionalTerminal.isPresent())
                terminal = optionalTerminal.get();
            else
                throw new AccountTransactionException("invalid merchant terminal"); /** invalid merchant */


            // Add transaction to Transaction table
            Transaction newTransaction = new Transaction();
            newTransaction.setAmountTransaction(amount);
            newTransaction.setCurrencyCode("KES");
            newTransaction.setDateTimeTransmission(this.formatDateTime(saleDto.getTransmissionDateTime()));
            newTransaction.setTerminal(terminal);
            newTransaction.setRecipientEmail("merchant@email.com");
            newTransaction.setPan(customerPan);
            newTransaction.setProcessingCode("00");
            newTransaction.setSourceAccount(sourceAccount);
            newTransaction.setDestinationAccount(destAccount);
            transactionRepository.save(newTransaction);

        } catch (Exception e) {
            throw new TransactionDeniedException(e.getMessage());
        }
    }


    /**
     *
     * @param transmissionDateTime
     * @return Formatted date
     * @throws Exception
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
}
