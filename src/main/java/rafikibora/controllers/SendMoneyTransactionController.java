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
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/transactions/send_money")
public class SendMoneyTransactionController {

    @Autowired
    private SendMoneyService sendMoneyService;

    @PostMapping
    public ResponseEntity<?> sendMoney(@Valid @RequestBody Transaction sendMoneyData) {
        System.out.println("==========================>" + sendMoneyData);
        //sendMoneyService.sendMoney(sendMoneyData);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
