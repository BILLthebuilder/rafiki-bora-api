package rafikibora;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rafikibora.model.account.Account;
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

        // Create customer
        User cust1 = new User();
        cust1.setFirstName("Maulid");
        cust1.setLastName("Bulle");
        cust1.setEmail("maulid@gmail.com");
        cust1.setUsername("mauli@gmail.com");
        cust1.setPhoneNo("555555");
        cust1.setPassword(passwordEncoder.encode("maulid"));
        cust1.setStatus(true);

        userrepos.save(cust1);

        // Add account info data
        Account account1 = new Account();
        account1.setAccountNumber("0714385056");
        account1.setName("Maulid Bulle");
        account1.setBalance(50000.0);
//        account1.setPhoneNumber("0714385056");
        account1.setPan("123");

        Account account2 = new Account();
        account2.setAccountNumber("0720305056");
        account2.setName("John Mulongo");
        account2.setBalance(50000.0);
//        account2.setPhoneNumber("0720305056");
        account2.setPan("123");

        accountRepository.save(account1);
        accountRepository.save(account2);
    }
}