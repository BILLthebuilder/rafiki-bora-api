//package rafikibora;
//
//
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import rafikibora.model.account.Account;
//import rafikibora.model.terminal.Terminal;
//import rafikibora.model.transactions.Transaction;
//import rafikibora.model.users.Role;
//import rafikibora.model.users.User;
//import rafikibora.model.users.UserRoles;
//import rafikibora.repository.*;
//
//import java.util.Date;
//
//
///**
// * Adds seed data to the database for testing purposes
// */
//@Transactional
//@Component
//@AllArgsConstructor
//public class SeedData
//        implements CommandLineRunner
//{
//
//    private final UserRepository userRepository;
//    private final AccountRepository accountRepository;
//    private final RoleRepository roleRepository;
//    private final TerminalRepository terminalRepository;
//    private final TransactionRepository transactionRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        // ################### ROLES ########################
//        // Admin
//        Role adminRole = new Role();
//        adminRole.setRoleName("ADMIN");
//        adminRole = roleRepository.save(adminRole);
//
//        // Merchant
//        Role merchantRole = new Role();
//        merchantRole.setRoleName("MERCHANT");
//        merchantRole = roleRepository.save(merchantRole);
//
//        // Customer
//        Role customerRole = new Role();
//        customerRole.setRoleName("CUSTOMER");
//        customerRole = roleRepository.save(customerRole);
//
//        // Agent
//        Role agentRole = new Role();
//        agentRole.setRoleName("AGENT");
//        agentRole = roleRepository.save(agentRole);
//
//
//        // ####### USERS ###########
//        // Admins
//        User admin1 = new User();
//        admin1.setFirstName("Jedidah");
//        admin1.setLastName("Wangeci");
//        admin1.setEmail("wangeci@mail.com");
//        admin1.setUsername("wangeci@mail.com");
//        admin1.setPhoneNo("0720942927");
//        admin1.setPassword(passwordEncoder.encode("Ellahruth019"));
//        admin1.setStatus(true);
//        admin1.getRoles().add(new UserRoles(admin1, adminRole));
//        userRepository.save(admin1);
//
//        User admin2 = new User();
//        admin2.setFirstName("Jedidah1");
//        admin2.setLastName("Wangeci1");
//        admin2.setEmail("wangeci1@mail.com");
//        admin2.setUsername("wangeci1@mail.com");
//        admin2.setPhoneNo("0720942928");
//        admin2.setPassword(passwordEncoder.encode("Ellahruth019"));
//        admin2.setStatus(true);
//        admin2.getRoles().add(new UserRoles(admin2, adminRole));
//        userRepository.save(admin2);
//
//
////        // Customers
////        User cust1 = new User();
////        cust1.setFirstName("Maulid");
////        cust1.setLastName("Bulle");
////        cust1.setEmail("maulid@gmail.com");
////        cust1.setUsername("mauli@gmail.com");
////        cust1.setPhoneNo("555555");
////        cust1.setPassword(passwordEncoder.encode("maulid"));
////        cust1.setStatus(true);
////        cust1.getRoles().add(new UserRoles(cust1, customerRole));
////        userRepository.save(cust1);
////
////        User cust2 = new User();
////        cust2.setFirstName("John");
////        cust2.setLastName("Paul");
////        cust2.setEmail("mulongojohnpaul1@gmail.com");
////        cust2.setUsername("mulongojohnpaul1@gmail.com");
////        cust2.setPhoneNo("999999");
////        cust2.setPassword(passwordEncoder.encode("mulongo"));
////        cust2.setStatus(true);
////        cust2.getRoles().add(new UserRoles(cust2, customerRole));
////        userRepository.save(cust2);
//
////        // Merchant
////
////        User merchant1 = new User();
////        merchant1.setFirstName("Merchant1-First");
////        merchant1.setLastName("Merchant1-Last");
////        merchant1.setEmail("mulongojohnpaul@gmail.com");
////        merchant1.setUsername("mulongojohnpaul@gmail.com");
////        merchant1.setPhoneNo("928273");
////        merchant1.setMid("123456789");
////        merchant1.setPassword(passwordEncoder.encode("merchant"));
////        merchant1.setStatus(true);
////        merchant1.getRoles().add(new UserRoles(merchant1, merchantRole));
////        userRepository.save(merchant1);
////
////        User merchant2 = new User();
////        merchant2.setFirstName("Merchant2-First");
////        merchant2.setLastName("Merchant2-Last");
////        merchant2.setEmail("merchant2@gmail.com");
////        merchant2.setUsername("merchant2@gmail.com");
////        merchant2.setPhoneNo("928273");
////        merchant2.setMid("112233445");
////        merchant2.setPassword(passwordEncoder.encode("merchant"));
////        merchant2.setStatus(true);
////        merchant2.getRoles().add(new UserRoles(merchant1, merchantRole));
////        userRepository.save(merchant2);
////
////
////        //#################### ACCOUNTS ########################
////        // Merchant accounts
////        Account merchantAcc1 = new Account();
////        merchantAcc1.setAccountNumber("555555");
////        merchantAcc1.setBalance(100000);
////        merchantAcc1.setName("Merchant One Account");
////        merchantAcc1.setStatus(true);
////        merchantAcc1.setPan("4478150055546780");
////        merchantAcc1.setAccountMaker(admin1);
////        merchantAcc1.setAccountChecker(admin2);
////        merchantAcc1.setPhoneNumber("0722555555");
////        merchantAcc1.setUser(merchant1);
////        accountRepository.save(merchantAcc1);
////
////        Account merchantAcc2 = new Account();
////        merchantAcc2.setAccountNumber("666666");
////        merchantAcc2.setBalance(250000);
////        merchantAcc2.setName("Mitishamba#2");
////        merchantAcc2.setStatus(true);
////        merchantAcc2.setPan("5196010116943992");
////        merchantAcc2.setUser(merchant2);
////        merchantAcc2.setAccountMaker(admin1);
////        merchantAcc2.setAccountChecker(admin2);
////        merchantAcc2.setPhoneNumber("0722444444");
////        accountRepository.save(merchantAcc2);
////
////        // Customer accounts
////        Account custAcc1 = new Account();
////        custAcc1.setAccountNumber("0714385056");
////        custAcc1.setName("Maulid Bulle");
////        custAcc1.setBalance(50000.0);
////        custAcc1.setPhoneNumber("0714385056");
////        custAcc1.setPan("123");
////        custAcc1.setAccountMaker(admin1);
////        custAcc1.setAccountChecker(admin2);
////        custAcc1.setUser(cust1);
////        accountRepository.save(custAcc1);
////
////        Account custAcc2 = new Account();
////        custAcc2.setAccountNumber("0720305056");
////        custAcc2.setName("John Mulongo");
////        custAcc2.setBalance(50000.0);
////        custAcc2.setPhoneNumber("0720305056");
////        custAcc2.setPan("123");
////        custAcc2.setUser(cust2);
////        custAcc2.setAccountMaker(admin1);
////        custAcc2.setAccountChecker(admin2);
////        accountRepository.save(custAcc2);
////
////        // ####################### TERMINALS #######################################
////        Terminal terminal1 = new Terminal();
////        terminal1.setTid("987654321");
////        terminal1.setSerialNo("qwerty");
////        terminal1.setModelType("Move220");
////        terminal1.setStatus(true);
////        terminal1.setMid(merchant1);
////        terminal1.setTerminalMaker(admin1);
////        terminal1.setTerminalChecker(admin2);
////        terminalRepository.save(terminal1);
////
////        // ####################### TRANSACTIONS #######################################
////        Transaction transaction1 = new Transaction();
////        transaction1.setPan("4478150055546780");
////        transaction1.setProcessingCode("260000");
////        transaction1.setAmountTransaction(13000);
////        transaction1.setDateTimeTransmission(new Date());
////        transaction1.setTerminalID("2345678910");
////        transaction1.setCurrencyCode("040");
////        transaction1.setRecipientEmail("mulungojohnpaul@gmail.com");
////        transactionRepository.save(transaction1);
//    }
//}
//
