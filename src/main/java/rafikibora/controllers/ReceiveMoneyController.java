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

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/receive_money")
public class ReceiveMoneyController {

    @Autowired
    private ReceiveMoneyService receiveMoneyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceiveMoneyResponseDto receiveMoneyTransaction(@RequestBody ReceiveMoneyRequestDto req, HttpServletRequest request) {
        System.out.println("=================================== INCOMING ISO MSG ===================================");
        System.out.println("Authorization header: "+request.getHeader("Authorization"));
        System.out.println("processingCode: "+ req.getProcessingCode());
        System.out.println("pan: " + req.getPan());
        System.out.println("txnAmount: " + req.getTxnAmount());
        System.out.println("currency: " + req.getCurrency());
        System.out.println("txnTransmissionDate: " + req.getTransmissionDateTime());
        System.out.println("tid: " + req.getTid());
        System.out.println("mid: " + req.getMid());
        System.out.println("receiveMoneyToken: "+req.getReceiveMoneyToken());
        System.out.println("=================================== INCOMING ISO MSG ===================================");
//        ReceiveMoneyResponseDto resp = new ReceiveMoneyResponseDto();
//        resp.setTxnAmount("5500");
//        resp.setMessage("00");
        ReceiveMoneyResponseDto resp = null;

        try {
            resp = receiveMoneyService.receiveMoney(req);
        } catch (Exception ex){
            System.out.println("ERROR MESSAGE: "+ex.getMessage());
            ex.printStackTrace();
            resp.setMessage("96");
        }
        return resp;
    }
}
