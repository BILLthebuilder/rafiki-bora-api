package rafikibora.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafikibora.model.terminal.Terminal;

import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long>{
<<<<<<< HEAD
//    Optional<Terminal> findById(Long id);
    Optional<Terminal> findById(Long id);
=======
    Optional<Terminal> findByTid(String tid);
>>>>>>> d4d899297290a7d39d40454502f0630bd1b9afd5

}




