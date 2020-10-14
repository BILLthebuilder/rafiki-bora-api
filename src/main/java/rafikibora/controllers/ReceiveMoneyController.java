package rafikibora.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafikibora.dto.ReceiveMoneyRequestDto;
import rafikibora.dto.ReceiveMoneyResponseDto;
import rafikibora.services.ReceiveMoneyService;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("api/auth/receive_money")
public class ReceiveMoneyController {

    @Autowired
    private ReceiveMoneyService receiveMoneyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceiveMoneyResponseDto receiveMoneyTransaction(@RequestBody ReceiveMoneyRequestDto req) {
        System.out.println("=================================== INCOMING ISO MSG ===================================");
        System.out.println("pcode: "+ req.getPcode());
        System.out.println("pan: " + req.getPan());
        System.out.println("txnAmount: " + req.getTxnAmount());
        System.out.println("txnCurrencyCode: " + req.getTxnCurrencyCode());
        System.out.println("txnLocalDate: " + req.getTxnLocalDate());
        System.out.println("txnLocalTime: " + req.getTxnLocalTime());
        System.out.println("tid: " + req.getTid());
        System.out.println("mid: " + req.getMid());
        System.out.println("srcAccount: " + req.getSrcAccount());
        System.out.println("destAccount: " + req.getDestAccount());
        System.out.println("authToken: "+ req.getAgentAuthToken());
        System.out.println("=================================== INCOMING ISO MSG ===================================");
        ReceiveMoneyResponseDto resp = null;
        try {
            resp = receiveMoneyService.receiveMoney(req);
        } catch (Exception ex){
            System.out.println("Exception message: "+ex.getMessage());
            ex.printStackTrace();
            resp.setMessage("GATEWAY ERROR");
        } finally {
            return resp;
        }
    }
}
