package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafikibora.model.users.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User findByEmail(String email);

    Set<User> findByRoles_Role_RoleNameContainingIgnoreCase(String roleName);
}
