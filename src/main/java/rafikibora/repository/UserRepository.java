package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rafikibora.model.users.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMid(String mid);


    //Optional<User> findByRoles( @Param("roleName") String roleName);

//    Set<User> findByroles(Long roleId);

    Set<User> findByRoles_RoleNameContainingIgnoreCase(String roleName);

    //Boolean existsByEmail(String email);
}
