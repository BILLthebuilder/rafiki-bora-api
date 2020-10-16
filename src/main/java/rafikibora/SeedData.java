package rafikibora;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.Role;
import rafikibora.model.users.User;
import rafikibora.model.users.UserRoles;
import rafikibora.repository.AccountRepository;
import rafikibora.repository.RoleRepository;
import rafikibora.repository.UserRepository;


/**
 * Adds seed data to the database for testing purposes
 */
@Transactional
@Component
@AllArgsConstructor
public class SeedData
        implements CommandLineRunner
{

    @Autowired
    UserRepository userrepos;

    @Autowired
    AccountRepository accountRepository;

    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args)
    {
        // Create users
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

        // Create roles
        // admin
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        adminRole = roleRepository.save(adminRole);

        // merchant
        Role merchantRole = new Role();
        merchantRole.setRoleName("MERCHANT");
        merchantRole = roleRepository.save(merchantRole);

        // customer
        Role customerRole = new Role();
        customerRole.setRoleName("CUSTOMER");
        customerRole = roleRepository.save(customerRole);

        // agent
        Role agentRole = new Role();
        agentRole.setRoleName("AGENT");
        agentRole = roleRepository.save(agentRole);

        // Assign roles to create users
        admin1.getRoles().add(new UserRoles(admin1, adminRole));
        userrepos.save(admin1);

        admin2.getRoles().add(new UserRoles(admin2, adminRole));
        userrepos.save(admin2);

        // Add merchant accounts
        Account merchantAcc1 = new Account();
        merchantAcc1.setAccountNumber("555555");
        merchantAcc1.setBalance(100000);
        merchantAcc1.setName("Mitishamba");
        merchantAcc1.setStatus(true);
        merchantAcc1.setPan("4478150055546780");
        merchantAcc1.setAccountMaker(admin1);
        merchantAcc1.setAccountChecker(admin2);
        merchantAcc1.setPhoneNumber("0722555555");
        accountRepository.save(merchantAcc1);

        Account merchantAcc2 = new Account();
        merchantAcc2.setAccountNumber("666666");
        merchantAcc2.setBalance(250000);
        merchantAcc2.setName("Mitishamba#2");
        merchantAcc2.setStatus(true);
        merchantAcc2.setPan("5196010116943992");
        merchantAcc2.setAccountMaker(admin1);
        merchantAcc2.setAccountChecker(admin2);
        merchantAcc2.setPhoneNumber("0722444444");
        accountRepository.save(merchantAcc2);
    }
}