package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rafikibora.model.transactions.Transaction;

import java.util.List;
import java.util.Optional;

//extends crud methods
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("SELECT SUM(amountTransaction) FROM Transaction")
    double totals();
    Optional<Transaction> findById(Integer id);
    Optional<Transaction> findByToken(String fundsToken);

    // All transactions
    List<Transaction> findAll();

    // All transactions on an account
    List<Transaction> findByPan(String pan);

//    find transaction  total by type
     @Query("SELECT SUM(s.amountTransaction)FROM Transaction s WHERE s.processingCode = :processingCode")
     Optional<Transaction> sum(@Param("processingCode")String processingCode);

    // All transactions by type
    List<Transaction> findByProcessingCode(String processingCode);

    // All transactions by merchant
    @Query("SELECT TRA, TER, MER FROM Transaction TRA JOIN TRA.terminal TER JOIN TER.mid MER WHERE MER.mid = :mid")
    List<Transaction> merchantTransactions(@Param("mid") String mid);

    // All transactions by terminal
    @Query("SELECT TRA, TER  FROM Transaction TRA JOIN TRA.terminal TER WHERE TER.tid = :tid")
    List<Transaction> terminalTransactions(@Param("tid") String tid);
}
