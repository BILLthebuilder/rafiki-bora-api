package rafikibora.repository;

import rafikibora.model.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByRoleName(String roleName);
}
