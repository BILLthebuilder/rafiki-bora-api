package rafikibora.services;

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
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * This class includes functionality to send money to someone.
 * A unique token is generated and sent by email to the intended
 * recipient for every instance of this transaction.
 */
@Service
@Slf4j
public class SendMoneyService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * This method is used to perform a send money transaction
     *
     * @param sendMoneyData
     */
    @Transactional
    public void sendMoney(Transaction sendMoneyData) {
        // Get required fields fields
        String merchantPan = sendMoneyData.getPan(); // 2
        String processingCode = sendMoneyData.getProcessingCode(); // 3
        Double amountToSend = sendMoneyData.getAmountTransaction(); //4
        String emailOfRecipient = sendMoneyData.getRecipientEmail(); // 47
        String currencyCode = sendMoneyData.getCurrencyCode(); // 49
        Date dateTimeTransmission = sendMoneyData.getDateTimeTransmission(); // 7
        String TID = sendMoneyData.getTID(); // 41

        System.out.println("######### pan: " + merchantPan);
        System.out.println("######### process code: " + processingCode);
        System.out.println("######### amount: " + amountToSend);
        System.out.println("######### email: " + emailOfRecipient);
        System.out.println("######### currency code: " + currencyCode);
        System.out.println("######### datetime: " + dateTimeTransmission);

        try {
            // Validate TID and MID
            validateTID(TID);

            // get merchant's account using pan
            Account merchantAccount = accountRepository.findByPan(merchantPan);

            // Add amount to merchant's account
            merchantAccount.setBalance(merchantAccount.getBalance() + amountToSend);

            // Generate withdrawal token
            String recipientToken = generateRecipientToken(9); // Generates a unique ID or length 9

            // Add token to transaction model
            sendMoneyData.setToken(recipientToken);

            // store transaction details
            transactionRepository.save(sendMoneyData);

            // send token to email of recipient

            emailService.sendEmail(emailOfRecipient, recipientToken);
        } catch (Exception ex) {
            log.error("Error sending money: " + ex.getMessage());
            throw new RafikiBoraException("Error sending money: " + ex.getMessage());
        }

    }

    /**
     * This method generates a unique token
     *
     * @param tokenLength Specifies the length of the token
     * @return UUID a unique token value
     */
    private String generateRecipientToken(int tokenLength) {
        return UUID.randomUUID().toString().substring(0, tokenLength);
    }

    /**
     * Ensures that the Terminal Identification is valid
     * @param TID Indentication Number
     */
    private void validateTID(String TID) {
        Optional<Terminal> terminal = terminalRepository.findByTid(TID);
        if (terminal == null) {
            throw new ResourceNotFoundException("Invalid Terminal Indentification Number");
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

}