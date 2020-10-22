package rafikibora.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rafikibora.RafikibApplication;
import rafikibora.model.transactions.Transaction;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.TerminalRepository;
import rafikibora.repository.TransactionRepository;
import rafikibora.repository.UserRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RafikibApplication.class) // Specifies main application context
class SendMoneyServiceTest {

    @Mock
    private EmailService emailService;


    @InjectMocks
    private SendMoneyService sendMoneyService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendMoney() throws IOException {
        Transaction data = new Transaction();
        data.setPan("4478150055546780"); // 2
        data.setProcessingCode("26000");
        data.setAmountTransaction(12000);
        data.setRecipientEmail("mulongojohnpaul@gmail.com");
        data.setCurrencyCode("040");
        data.setDateTime("2010200000");
        data.setTerminalID("123456789");

        assertEquals(true, sendMoneyService.sendMoney(data));
    }

    @Test
    void sendMoneyInvalidEmail() throws IOException {
        Transaction data = new Transaction();
        data.setPan("4478150055546780"); // 2
        data.setProcessingCode("26000");
        data.setAmountTransaction(12000);
        data.setRecipientEmail("mulongojohnpaul@gma.com"); // Bad email
        data.setCurrencyCode("040");
        data.setDateTime("201020000000");
        data.setTerminalID("123456789");

        assertEquals(false, sendMoneyService.sendMoney(data));
    }

}