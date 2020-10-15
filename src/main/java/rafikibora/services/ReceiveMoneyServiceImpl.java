package rafikibora.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;
import rafikibora.exceptions.ResourceNotFoundException;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.transactions.Transaction;
import rafikibora.model.users.User;
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
public class ReceiveMoneyServiceImpl implements ReceiveMoneyService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    @Transactional
    public ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto req) throws ParseException {
        Optional<User> optionalMerchant;
        Optional<Terminal> optionalTerminal;
        User merchant;
        Terminal terminal;
        double amount = Double.parseDouble(req.getTxnAmount());
        double newAccountBalance;
        double accountBalance;
        String currencyCode = this.formatCurrencyCode(req.getTxnCurrencyCode());
        Transaction transaction = new Transaction();

        // Get merchant
        optionalMerchant = userRepository.findByMid(req.getMid());
        if(optionalMerchant.isPresent())
            merchant = optionalMerchant.get();
        else
            throw new ResourceNotFoundException("Merchant Not Found");

        accountBalance = merchant.getUserAccount().getBalance();
        if(accountBalance >= amount){
            newAccountBalance = accountBalance - amount;
            merchant.getUserAccount().setBalance(newAccountBalance);
            userRepository.save(merchant);
        }

        // Get terminal
        optionalTerminal = terminalRepository.findByTid(req.getTid());
        if(optionalTerminal.isPresent())
            terminal = optionalTerminal.get();
        else
            throw new ResourceNotFoundException("Merchant Not Found");

        transaction.setAmountTransaction(amount);
        transaction.setCurrencyCode(currencyCode);
        transaction.setDateTimeTransmission(this.formatDateTime(req.getTransmissionDateTime()));
        /**
         * If withdrawing with token, merchant account is the debit account.
         * If withdrawing with card, customer account is the debit account.
         */
        transaction.setSourceAccount(merchant.getUserAccount());
        transaction.setTerminal(terminal);
        transaction.setPan(req.getPan());
        transaction.setProcessingCode(req.getPcode());
        transactionRepository.save(transaction);

        if(merchant != null) {
            System.out.println("**************Merchant Details**************");
            System.out.println("user id: "+ merchant.getUserid());
            System.out.println("buss name: "+merchant.getBusinessName());
            System.out.println("email: "+merchant.getEmail());
            System.out.println("name: "+merchant.getFirstName() + merchant.getLastName());
            System.out.println("phone no: "+merchant.getPhoneNo());
            System.out.println("mid: "+merchant.getMid());
            System.out.println("tid: "+terminal.getTid());
            System.out.println("account number: "+merchant.getUserAccount().getAccountNumber());
            System.out.println("account balance: "+merchant.getUserAccount().getBalance());
            System.out.println("business name: "+merchant.getUserAccount().getName());
            System.out.println("pan: "+merchant.getUserAccount().getPan());
            System.out.println("**************Merchant Details**************");
        }else{
            System.out.println("**************Merchant Details**************");
            System.out.println("No merchant Found...");
            System.out.println("**************Merchant Details**************");
        }

        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
        resp.setMessage("successful");
        return resp;
    }


    /**
     *
     * @param transmissionDateTime
     * @return Formatted date
     * @throws ParseException
     */
    private Date formatDateTime(String transmissionDateTime) throws ParseException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String month = transmissionDateTime.substring(0,2);
        String day = transmissionDateTime.substring(2,4);
        String hour = transmissionDateTime.substring(4,6);
        String min = transmissionDateTime.substring(6,8);
        String sec = transmissionDateTime.substring(8);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String fullDateTime = year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec;
        SimpleDateFormat transmitDateTime = new SimpleDateFormat(pattern);
        Date date = transmitDateTime.parse(fullDateTime);
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
            default:
                ;
        }
        return code;
    }
}
