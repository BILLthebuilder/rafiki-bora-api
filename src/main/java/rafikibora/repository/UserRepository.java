package rafikibora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rafikibora.model.account.Account;
import rafikibora.model.users.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    Optional<User> findByMid(String mid);

    User findByEmail(String email);

    Set<User> findByRoles_Role_RoleNameContainingIgnoreCase(String roleName);

    @Query("SELECT u FROM User u WHERE UPPER(u.roles) = UPPER(:roles)")
    public List<User> find(@Param("roles") String roles);
}
