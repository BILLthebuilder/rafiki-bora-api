//package rafikibora.controllers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import rafikibora.model.transactions.Transaction;
//import rafikibora.services.DepositOrSaleService;
//
//@RestController
//@RequestMapping("/api/transactions/deposit")
//public class DepositSaleController {
//    @Autowired
//    private DepositOrSaleService depositOrSaleService;
//
//    @PostMapping
//    public ResponseEntity<?> createDeposit(@RequestBody Transaction depositSaleData) {
//        System.out.println("=========== request received =======");
//        System.out.println(depositSaleData);
//        depositOrSaleService.performDepositOrSale(depositSaleData);
//
//        if(depositSaleData == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deposit transaction is invalid");
//        }
//        return new ResponseEntity<>(HttpStatus.CREATED);
//
//    }
//}
