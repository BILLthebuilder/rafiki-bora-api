package rafikibora.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafikibora.model.transactions.Transaction;
import rafikibora.services.SendMoneyService;

/**
 * This controller handles all transactions pertaining to sending money
 * to a given user.
 */
@RestController
@RequestMapping(value = "/api/transactions/send_money")
public class SendMoneyTransactionController {

    @Autowired
    private SendMoneyService sendMoneyService;

    @PostMapping
    public ResponseEntity<?> sendMoney(@RequestBody Transaction sendMoneyData) {
        System.out.println("=============================================> " + sendMoneyData);
        boolean isOK = sendMoneyService.sendMoney(sendMoneyData);
        if (isOK) {
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DECLINED");

    }
}
