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


    /**
     *
     * @param req
     * @return
     * @throws AccountTransactionException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto req) throws AccountTransactionException {
        Optional<Terminal> optionalTerminal;
        Optional<Transaction> optionalTransaction;
        Optional<Account> optionalMerchantAccount;
        Optional<User> optionalMerchant;
        User merchant;
        Transaction transaction;
        Account merchantAccount;
        Terminal terminal;
        double amount;
        double accountBalance;

        /** get amount to this token */
        optionalTransaction = transactionRepository.findByToken(req.getReceiveMoneyToken());
        if(optionalTransaction.isPresent()) {
            transaction = optionalTransaction.get();
            amount = transaction.getAmountTransaction();
        }
        else
            throw new AccountTransactionException("12"); /** invalid transaction */


        /** get merchant account */
        optionalMerchantAccount = accountRepository.findByPan(req.getPan());
        if(optionalMerchantAccount.isPresent()) {
            merchantAccount = optionalMerchantAccount.get();
            accountBalance = merchantAccount.getBalance();

            /** debit merchant */
            if(accountBalance >= transaction.getAmountTransaction()){
                merchantAccount.setBalance((accountBalance - amount));
                accountRepository.save(merchantAccount);
            }else
                throw new AccountTransactionException("51"); /** not sufficient amounts */

        } else
            throw new AccountTransactionException("56"); /** no card record */


        /** get terminal and merchant used in transaction */
        optionalTerminal = terminalRepository.findByTid(req.getTid());
        if(optionalTerminal.isPresent())
            terminal = optionalTerminal.get();
        else
            throw new AccountTransactionException("03"); /** invalid merchant */

        optionalMerchant = userRepository.findByMid(req.getTid());
        if(optionalMerchant.isPresent())
            merchant= optionalMerchant.get();
        else
            throw new AccountTransactionException("03"); /** invalid merchant */

        /** save transaction */
        Transaction newTransaction = new Transaction();
        newTransaction.setAmountTransaction(amount);
        newTransaction.setCurrencyCode("KES");
        newTransaction.setDateTimeTransmission(this.formatDateTime(req.getTransmissionDateTime()));
        newTransaction.setTerminal(terminal);
        newTransaction.setPan(req.getPan());
        newTransaction.setProcessingCode(req.getProcessingCode());
        newTransaction.setSourceAccount(merchant.getUserAccount());
        transactionRepository.save(newTransaction);

        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
        resp.setMessage("00");
        resp.setTxnAmount(String.valueOf(amount));
        return resp;
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
