package rafikibora;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.model.users.Role;
import rafikibora.model.users.User;
import rafikibora.model.users.UserRoles;
import rafikibora.repository.RoleRepository;
import rafikibora.repository.UserRepository;


@Transactional
@Component
@AllArgsConstructor
public class SeedData
        implements CommandLineRunner
{

    @Autowired
    UserRepository userrepos;

    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)
    {
        // Adding SeedData for admins
        User admin1 = new User();
        admin1.setFirstName("Jedidah");
        admin1.setLastName("Wangeci");
        admin1.setEmail("wangeci@mail.com");
        admin1.setUsername("wangeci@mail.com");
        admin1.setPhoneNo("0720942927");
        admin1.setPassword(passwordEncoder.encode("Ellahruth019"));
        admin1.setStatus(true);

        User admin2 = new User();
        admin2.setFirstName("Jedidah1");
        admin2.setLastName("Wangeci1");
        admin2.setEmail("wangeci1@mail.com");
        admin2.setUsername("wangeci1@mail.com");
        admin2.setPhoneNo("0720942928");
        admin2.setPassword(passwordEncoder.encode("Ellahruth019"));
        admin2.setStatus(true);

        Role r1 = new Role();
        r1.setRoleName("ADMIN");
        r1 = roleRepository.save(r1);

        admin1.getRoles().add(new UserRoles(admin1, r1));
        userrepos.save(admin1);

        admin2.getRoles().add(new UserRoles(admin2, r1));
        userrepos.save(admin2);
    }
}