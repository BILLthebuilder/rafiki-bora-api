package rafikibora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafikibora.dto.SaleDto;
import rafikibora.services.SaleService;


@RestController
@RequestMapping("/api/sale")
public class SaleController {
    @Autowired
     private SaleService saleService;

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody SaleDto saleDto) {
        System.out.println("=========== Sale request received =======");
        System.out.println("pan: "+saleDto.getPan());
        System.out.println("processingCode: "+saleDto.getProcessingCode());
        System.out.println("amount: "+saleDto.getAmountTransaction());
        System.out.println("terminal: "+saleDto.getTerminal());
        System.out.println("merchant: "+saleDto.getMerchant());
        System.out.println("currency: "+saleDto.getCurrencyCode());
        System.out.println("=========== Sale request received =======");

        boolean status;
        try{
            saleService.performSale(saleDto);
            status = true;
        }catch (Exception ex){
            ex.printStackTrace();
            status = false;
        }
        if(status){
            return ResponseEntity.status(HttpStatus.CREATED).body("Sale transaction is valid");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sale transaction is invalid");
        }
    }
}
