package rafikibora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafikibora.model.transactions.Transaction;
import rafikibora.services.SaleService;


@RestController
@RequestMapping("/depositSale")
public class SaleController {
    @Autowired
     private SaleService saleService;

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody Transaction saleDto) {
        System.out.println("=========== Sale request received =======");
        System.out.println(saleDto);
        saleService.performSale(saleDto);

        if(saleDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deposit transaction is invalid");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
