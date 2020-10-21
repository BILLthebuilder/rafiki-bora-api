package rafikibora.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafikibora.dto.DepositDto;
import rafikibora.model.transactions.Transaction;
import rafikibora.services.DepositService;

@RestController
@RequestMapping("/api/transactions/deposit")
public class DepositController {
    @Autowired
    private DepositService depositService;

//    @Autowired
//    Transaction transaction;

    @PostMapping
    public ResponseEntity<?> createDeposit(@RequestBody DepositDto depositDto) {


        System.out.println("===========Deposit request received =======");
        System.out.println("merchantPan: "+ depositDto.getMerchantPan());
        System.out.println("customerPan: "+ depositDto.getCustomerPan());
        System.out.println("amountTransaction: "+depositDto.getAmountTransaction());
        System.out.println("DateTimeTransmission: "+depositDto.getDateTimeTransmission());
        System.out.println("terminal: "+depositDto.getTerminal());
        System.out.println("merchant: "+depositDto.getMerchant());
        System.out.println("currencyCode: "+depositDto.getCurrencyCode());
        System.out.println("processingCode: "+depositDto.getProcessingCode());
        System.out.println("===========Deposit request received =======");

        boolean status;
        try{
            depositService.performDeposit(depositDto);
            status = true;
        }catch (Exception ex){
            ex.printStackTrace();
            status = false;
        }
        if(status){
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deposit transaction is invalid");
        }
    }
}
