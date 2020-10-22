package rafikibora.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.exceptions.RafikiBoraException;
import rafikibora.exceptions.ResourceNotFoundException;
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
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class includes functionality to send money to someone.
 * A unique token is generated and sent by email to the intended
 * recipient for every instance of this transaction.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SendMoneyService {

    private final String PROCESSING_CODE = "26000";
    /** Formats a date with such a format as: 04-09-2019 01:45:48 */
    private final String DATE_TIME_FORMAT_BIT_7 = "dd-MM-yyyy hh:mm:ss";

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TerminalRepository terminalRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    /**
     * This method is used to perform a send money transaction
     *
     * @param sendMoneyData
     * @return
     */
    @Transactional
    public boolean sendMoney(Transaction sendMoneyData) {

        System.out.println("###############################: Send Money Data: " + sendMoneyData);
        // Get required fields
        String merchantPan = sendMoneyData.getPan(); // 2
        String processingCode = sendMoneyData.getProcessingCode(); // 3
        Double amountToSend = sendMoneyData.getAmountTransaction(); //4
        String emailOfRecipient = sendMoneyData.getRecipientEmail(); // 47
        String currencyCode = sendMoneyData.getCurrencyCode(); // 49
        String dateTime = sendMoneyData.getDateTime(); // 7
        String terminalID = sendMoneyData.getTerminalID(); // 41
        String merchantID = sendMoneyData.getMerchantID(); // 42

        // Set a default value for the processing code if none provided
        processingCode = (processingCode == null ? PROCESSING_CODE: processingCode);

        try {
            Date date = parseDateTime(dateTime);
            dateTime = "" + date;
            System.out.println("=============================== " + dateTime);
        } catch (ParseException | IndexOutOfBoundsException ex) {
            log.warn("Date could not be parsed: " + ex.getMessage());
        }

        try {
            validateTID(terminalID);
            validateMID(merchantID);

            Optional<Account> merchantAccount = accountRepository.findByPan(merchantPan);
            // Add amount to merchant's account
            merchantAccount.get().setBalance(merchantAccount.get().getBalance() + amountToSend);
            String recipientToken = "" + generateRecipientToken();
            sendMoneyData.setToken(recipientToken);
            sendMoneyData.setDateTime(dateTime);
            sendMoneyData.setProcessingCode(processingCode);
            sendMoneyData.setCurrencyCode(currencyCode);
            transactionRepository.save(sendMoneyData);
            emailService.sendEmail(emailOfRecipient, recipientToken);
        } catch (Exception ex) {
            log.error("Error sending money: " + ex.getMessage());
            throw new RafikiBoraException("Error sending money: " + ex.getMessage());
        }
        return true;
    }

    /**
     * This method generates a unique 9 digit code
     *
     * @return  a unique code made of integer values
     */
    private int generateRecipientToken() {
        return ThreadLocalRandom.current().nextInt(1, 999999999);
    }

    /**
     * Ensures that the Terminal Identification is valid
     * @param TID Indentication Number
     */
    private void validateTID(String TID) {
        Optional<Terminal> terminal = terminalRepository.findByTid(TID);
        if (!terminal.isPresent()) {
            throw new ResourceNotFoundException("Invalid Terminal Identification Number");
        }
    }

    /**
     * Checks that the Merchant Identification number is valid
     *
     * @param MID Merchant Identification Number
     */
    private void validateMID(String MID) {
        Optional<User> merchant = userRepository.findByMid(MID);
        if (merchant == null) {
            throw new ResourceNotFoundException("Invalid Merchant Identification Number");
        }
    }

    /**
     * Formats a string representation of date and time
     * into a valid format that can be parsed by the DateFormatter object.
     *
     * @param dateTimeString a string formatted as 'yymmddhhmmss'
     * @return a string with the format 'dd-MM-yyyy hh:mm:ss'
     */
    private Date parseDateTime(String dateTimeString) throws ParseException {
        Date date = null;
        String formattedDateTimeString = null;
        String year =  dateTimeString.substring(0, 2);
        String month =  dateTimeString.substring(2, 4);
        String day =  dateTimeString.substring(4, 6);
        String hr =  dateTimeString.substring(6, 8);
        String min =  dateTimeString.substring(8, 10);
        String sec =  "00";

        formattedDateTimeString = day + "-" + month + "-" + "20" + year + " " + hr + ":" + min + ":" + sec;
        SimpleDateFormat d = new SimpleDateFormat(DATE_TIME_FORMAT_BIT_7);
        date = d.parse(formattedDateTimeString);

        return date;
    }
}