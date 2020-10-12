package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafikibora.model.transactions.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findByResultCode(String resultCode);
}
