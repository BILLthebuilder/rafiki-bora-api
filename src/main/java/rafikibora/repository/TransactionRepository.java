package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafikibora.model.transactions.Transaction;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Optional<Transaction> findById(Integer id);
    Optional<Transaction> findByToken(String fundsToken);

}
