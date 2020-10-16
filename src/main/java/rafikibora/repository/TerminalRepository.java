package rafikibora.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafikibora.model.terminal.Terminal;

import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long>{

    Optional<Terminal> findById(Long id);

    Optional<Terminal> findByTid(String tid);
}




