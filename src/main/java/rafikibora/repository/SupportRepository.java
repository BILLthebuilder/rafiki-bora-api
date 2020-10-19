package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafikibora.model.support.Support;
import rafikibora.model.terminal.Terminal;

    @Repository
    public interface SupportRepository extends JpaRepository<Support, Long> {

    }
