package rafikibora.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;
import rafikibora.exceptions.ResourceNotFoundException;
import rafikibora.helpers.Transaction;
import rafikibora.model.account.Account;
import rafikibora.model.users.User;
import rafikibora.repository.AccountRepository;
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
    private AccountRepository accountRepository;

    @Override
    public ReceiveMoneyResponseDto receiveMoney(ReceiveMoneyRequestDto req) throws ParseException {
        Optional<User> optionalMerchant;
        Optional<Account> optionalMerchantAccount;
        User merchant;
        Account merchantAccount;
        double amount = Double.parseDouble(req.getTxnAmount());
        String currencyCode = this.formatCurrencyCode(req.getTxnCurrencyCode());

//        // Get merchant
//        optionalMerchant = userRepository.findByEmail(req.getMid());
//        if(optionalMerchant.isPresent())
//            merchant = optionalMerchant.get();
//        else
//            throw new ResourceNotFoundException("Merchant Not Found");
//
//        // Get merchant bank account
//        optionalMerchantAccount = accountRepository.findByAccountNumber(merchant.getUserAccount().getAccountNumber());
//        if(optionalMerchantAccount.isPresent())
//            merchantAccount =  optionalMerchantAccount.get();
//        else
//            throw new ResourceNotFoundException("Merchant Account Not Found");


        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
        resp.setMessage("successful");
        resp.setAmount(amount);
        resp.setCurrencyCode(currencyCode);
        resp.setTransmissionDateTime(this.formatDateTime(req.getTransmissionDateTime()));
        resp.setTransactionDateTime(this.formatDateTime(req.getTxnLocalDate()+req.getTxnLocalTime()));

        return resp;
    }


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
