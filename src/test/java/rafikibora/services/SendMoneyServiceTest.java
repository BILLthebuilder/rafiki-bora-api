package rafikibora.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rafikibora.RafikibApplication;
import rafikibora.model.transactions.Transaction;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RafikibApplication.class) // Specifies main application context
class SendMoneyServiceTest {

    @Autowired
    private SendMoneyService sendMoneyService;

    @BeforeEach
    void setUp() {
        // mocks -> fake data
        // stubs -> fake methods
        // Java -> both mocks
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendMoney() {
        Transaction data = new Transaction();
        data.setPan("4478150055546780"); // 2
        data.setProcessingCode("26000");
        data.setAmountTransaction(12000);
        data.setRecipientEmail("mulongojohnpaul@gmail.com");
        data.setCurrencyCode("040");
        data.setDateTime("201020000000");
        data.setTerminalID("123456789");

        assertEquals(true, sendMoneyService.sendMoney(data));
    }
}