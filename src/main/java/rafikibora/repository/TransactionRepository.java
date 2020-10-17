package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rafikibora.model.transactions.Transaction;

import java.util.Optional;

//extends crud methods
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("SELECT SUM(amountTransaction) FROM Transaction")
    double totals();
    Optional<Transaction> findById(Integer id);
    Optional<Transaction> findByToken(String fundsToken);

}
