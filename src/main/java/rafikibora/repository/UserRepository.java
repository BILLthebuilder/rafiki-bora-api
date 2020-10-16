package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafikibora.model.users.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    Optional<User> findByMid(String mid);

    User findByEmail(String email);

    Set<User> findByRoles_Role_RoleNameContainingIgnoreCase(String roleName);
}
