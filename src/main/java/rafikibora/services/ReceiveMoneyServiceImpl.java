package rafikibora.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;
import rafikibora.exceptions.AccountTransactionException;
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
@AllArgsConstructor
public class ReceiveMoneyServiceImpl implements ReceiveMoneyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Optional<User> optionalMerchant;
    private Optional<Terminal> optionalTerminal;
    private Optional<Transaction> optionalTransaction;
    private Optional<Account> optionalCustomerAccount;
    private Transaction transaction;
    private Account customerAccount;
    private User merchant;
    private Terminal terminal;
    private double amount;
    private double newAccountBalance;
    private double accountBalance;
    private String currencyCode;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReceiveMoneyResponseDto withdrawMoney(ReceiveMoneyRequestDto req) throws AccountTransactionException {
        this.amount = Double.parseDouble(req.getTxnAmount());
        this.currencyCode = this.formatCurrencyCode(req.getTxnCurrencyCode());

        /** debit customer account */
        this.optionalCustomerAccount = this.accountRepository.findByPan(req.getPan());
        if(this.optionalCustomerAccount.isPresent())
            this.customerAccount = this.optionalCustomerAccount.get();
        else
            throw new AccountTransactionException("56"); /** no card record */

        this.accountBalance = this.customerAccount.getBalance();
        if(this.accountBalance >= this.amount){
            this.customerAccount.setBalance((this.accountBalance - this.amount));
            this.accountRepository.save(this.customerAccount);
        }else
            throw new AccountTransactionException("51"); /** not sufficient amounts */


        /** credit merchant account */
        this.optionalMerchant = this.userRepository.findByMid(req.getMid());
        if(this.optionalMerchant.isPresent())
            this.merchant = this.optionalMerchant.get();
        else
            throw new AccountTransactionException("03"); /** invalid merchant */

        this.accountBalance = this.merchant.getUserAccount().getBalance();
        this.merchant.getUserAccount().setBalance((this.accountBalance + this.amount));
        this.userRepository.save(this.merchant);


        /** create new transaction record */
        this.createTransactionRecord(req);


        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
        resp.setMessage("00");
        return resp;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto req) throws AccountTransactionException {
        this.amount = Double.parseDouble(req.getTxnAmount());
        this.currencyCode = this.formatCurrencyCode(req.getTxnCurrencyCode());

        this.optionalMerchant = this.userRepository.findByMid(req.getMid());
        if(this.optionalMerchant.isPresent())
            this.merchant =  this.optionalMerchant.get();
        else
            throw new AccountTransactionException("03"); /** invalid merchant */

        /** debit merchant account */
        this.accountBalance = this.merchant.getUserAccount().getBalance();
        if(this.accountBalance >= this.amount){
            this.newAccountBalance = this.accountBalance - this.amount;
            this.merchant.getUserAccount().setBalance(this.newAccountBalance);
            userRepository.save(this.merchant);
        }
        else
            throw new AccountTransactionException("51"); /** not sufficient amounts */


        /**
         * credit a suspense account
         *
         */

        /** create new transaction record */
        this.createTransactionRecord(req);


        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
        resp.setMessage("00");
        return resp;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReceiveMoneyResponseDto details (ReceiveMoneyRequestDto req) throws AccountTransactionException{
        Optional<Transaction> optionalTransaction;
        Transaction transaction;

        optionalTransaction = transactionRepository.findByToken(req.getFundsToken());
        if(optionalTransaction.isPresent())
            transaction = optionalTransaction.get();
        else
            throw new AccountTransactionException("12"); /** invalid transaction */

        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
        resp.setTxnAmount(Double.toString(transaction.getAmountTransaction()));
        resp.setCurrencyCode(this.formatCurrencyCode(transaction.getAmountTransactionCurrencyCode()));
        resp.setMessage("00"); /** completed successfully */

        return resp;
    }


    @Transactional(rollbackFor = Exception.class)
    private void createTransactionRecord(ReceiveMoneyRequestDto req) throws AccountTransactionException{
        this.optionalTerminal = this.terminalRepository.findByTid(req.getTid());
        if(this.optionalTerminal.isPresent())
            this.terminal = this.optionalTerminal.get();
        else
            throw new AccountTransactionException("03"); /** invalid merchant */

        this.transaction.setAmountTransaction(this.amount);
        this.transaction.setAmountTransactionCurrencyCode(this.currencyCode);
        this.transaction.setDateTimeLocalTransaction(this.formatDateTime(req.getTxnLocalDate()+req.getTxnLocalTime()));
        this.transaction.setDateTimeTransmission(this.formatDateTime(req.getTransmissionDateTime()));
        this.transaction.setTerminal(this.terminal);
        this.transaction.setMerchant(this.merchant);
        this.transaction.setPosConditionCode(req.getPosConditionCode());
        this.transaction.setPan(req.getPan());
        this.transaction.setProcessingCode(req.getPcode());
        this.transaction.setStan(req.getStan());
        this.transactionRepository.save(this.transaction);
    }


    /**
     *
     * @param transmissionDateTime
     * @return Formatted date
     * @throws Exception
     */
    private Date formatDateTime(String transmissionDateTime) throws AccountTransactionException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String month = transmissionDateTime.substring(0,2);
        String day = transmissionDateTime.substring(2,4);
        String hour = transmissionDateTime.substring(4,6);
        String min = transmissionDateTime.substring(6,8);
        String sec = transmissionDateTime.substring(8);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String fullDateTime = year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec;
        SimpleDateFormat transmitDateTime = new SimpleDateFormat(pattern);
        Date date;
        try{
            date = transmitDateTime.parse(fullDateTime);
        }catch (ParseException ex){
            throw new AccountTransactionException("Failed to parse transaction date");
        }
        return date;
    }


    /**
     *
     * @param currencyCode
     * @return currency
     */
    private String formatCurrencyCode(String currencyCode){
        String code = "";
        switch(currencyCode){
            case "810":
                code = "KES";
                break;
            case "840":
                code = "USD";
                break;
            case "KES":
                code = "810";
                break;
            case "USD":
                code = "840";
                break;
            default:
                ;
        }
        return code;
    }

}
