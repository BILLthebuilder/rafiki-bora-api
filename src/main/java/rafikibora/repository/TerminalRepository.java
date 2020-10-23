package rafikibora.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafikibora.model.terminal.Terminal;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long>{
    Terminal findById(long id);
    Optional<Terminal> findByTid(String tid);

    List<Terminal> findByMid_MidIsNull();

    // Lists all terminals that:
    // - Belong to a merchant with merchant ID 'mid'
    // - Are not assigned to an agent
    List<Terminal> findByMidAndAgentIsNull(String mid);
}




