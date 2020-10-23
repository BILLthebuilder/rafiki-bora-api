//package rafikibora;
//
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
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
//import java.time.LocalDateTime;
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
//        // Customers
//        User cust1 = new User();
//        cust1.setFirstName("ANTHONY");
//        cust1.setLastName("MUTHUMA");
//        cust1.setEmail("anthony.muthuma@rafiki.com");
//        cust1.setUsername("anthony.muthuma@rafiki.com");
//        cust1.setPhoneNo("0714385058");
//        cust1.setPassword(passwordEncoder.encode("password"));
//        cust1.setStatus(true);
//        cust1.getRoles().add(new UserRoles(cust1, customerRole));
//        userRepository.save(cust1);
//
//        User cust2 = new User();
//        cust2.setFirstName("RUFUSY");
//        cust2.setLastName("IDACHI");
//        cust2.setEmail("rufusy.idachi@rafiki.com");
//        cust2.setUsername("rufusy.idachi@rafiki.com");
//        cust2.setPhoneNo("0714385059");
//        cust2.setPassword(passwordEncoder.encode("password"));
//        cust2.setStatus(true);
//        cust2.getRoles().add(new UserRoles(cust2, customerRole));
//        userRepository.save(cust2);
//
//        // Merchant
//
//        User merchant1 = new User();
//        merchant1.setFirstName("BETTY");
//        merchant1.setLastName("KIRII");
//        merchant1.setEmail("betty.kirii@rafiki.com");
//        merchant1.setUsername("betty.kirii@rafiki.com");
//        merchant1.setBusinessName("BETTY'S SHOP");
//        merchant1.setPhoneNo("0714385056");
//        merchant1.setMid("123456789123456");
//        merchant1.setPassword(passwordEncoder.encode("password"));
//        merchant1.setStatus(true);
//        merchant1.getRoles().add(new UserRoles(merchant1, merchantRole));
//        userRepository.save(merchant1);
//
//        User merchant2 = new User();
//        merchant2.setFirstName("BILL");
//        merchant2.setLastName("BRANDON");
//        merchant2.setEmail("bill.brandon@rafiki.com");
//        merchant2.setUsername("bill.brandon@rafiki.com");
//        merchant2.setBusinessName("BILL'S SHOP");
//        merchant2.setPhoneNo("0714385057");
//        merchant2.setMid("123456789123457");
//        merchant2.setPassword(passwordEncoder.encode("password"));
//        merchant2.setStatus(true);
//        merchant2.getRoles().add(new UserRoles(merchant2, merchantRole));
//        userRepository.save(merchant2);
//
//
//        //#################### ACCOUNTS ########################
//        // Merchant accounts
//        Account merchantAcc1 = new Account();
//        merchantAcc1.setAccountNumber("0714385056");
//        merchantAcc1.setBalance(100000);
//        merchantAcc1.setName("BETTY'S SHOP");
//        merchantAcc1.setStatus(true);
//        merchantAcc1.setPan("5196010116643992");
//        merchantAcc1.setAccountMaker(admin1);
//        merchantAcc1.setAccountChecker(admin2);
//        merchantAcc1.setPhoneNumber("0714385056");
//        merchantAcc1.setUser(merchant1);
//        accountRepository.save(merchantAcc1);
//
//        Account merchantAcc2 = new Account();
//        merchantAcc2.setAccountNumber("0714385057");
//        merchantAcc2.setBalance(250000);
//        merchantAcc2.setName("BILL'S SHOP");
//        merchantAcc2.setStatus(true);
//        merchantAcc2.setPan("4478150181885102");
//        merchantAcc2.setUser(merchant2);
//        merchantAcc2.setAccountMaker(admin1);
//        merchantAcc2.setAccountChecker(admin2);
//        merchantAcc2.setPhoneNumber("0714385057");
//        accountRepository.save(merchantAcc2);
//
//        // Customer accounts
//        Account custAcc1 = new Account();
//        custAcc1.setAccountNumber("0714385058");
//        custAcc1.setName("ANTHONY MUTHUMA");
//        custAcc1.setBalance(50000.0);
//        custAcc1.setPhoneNumber("0714385058");
//        custAcc1.setPan("4478150096571201");
//        custAcc1.setAccountMaker(admin1);
//        custAcc1.setAccountChecker(admin2);
//        custAcc1.setUser(cust1);
//        accountRepository.save(custAcc1);
//
//        Account custAcc2 = new Account();
//        custAcc2.setAccountNumber("0714385059");
//        custAcc2.setName("RUFUSY IDACHI");
//        custAcc2.setBalance(50000.0);
//        custAcc2.setPhoneNumber("0714385059");
//        custAcc2.setPan("5196010174673147");
//        custAcc2.setUser(cust2);
//        custAcc2.setAccountMaker(admin1);
//        custAcc2.setAccountChecker(admin2);
//        accountRepository.save(custAcc2);
//
//        // ####################### TERMINALS #######################################
//        Terminal terminal1 = new Terminal();
//        terminal1.setTid("00000001");
//        terminal1.setSerialNo("2006173003221017313880837");
//        terminal1.setModelType("Move/2500");
//        terminal1.setStatus(true);
//        terminal1.setMid(merchant1);
//        terminal1.setTerminalMaker(admin1);
//        terminal1.setTerminalChecker(admin2);
//        terminalRepository.save(terminal1);
//
//        Terminal terminal2 = new Terminal();
//        terminal2.setTid("00000002");
//        terminal2.setSerialNo("2006173003221017313880838");
//        terminal2.setModelType("Move/2500");
//        terminal2.setStatus(true);
//        terminal2.setMid(merchant1);
//        terminal2.setTerminalMaker(admin1);
//        terminal2.setTerminalChecker(admin2);
//        terminalRepository.save(terminal2);
//
//        Terminal terminal3 = new Terminal();
//        terminal3.setTid("00000003");
//        terminal3.setSerialNo("2006173003221017313880839");
//        terminal3.setModelType("Move/2500");
//        terminal3.setStatus(true);
//        terminal3.setMid(merchant2);
//        terminal3.setTerminalMaker(admin1);
//        terminal3.setTerminalChecker(admin2);
//        terminalRepository.save(terminal3);
//
//        // ####################### TRANSACTIONS #######################################
//        Transaction transaction1 = new Transaction();
//        transaction1.setPan("4478150055546780");
//        transaction1.setProcessingCode("260000");
//        transaction1.setAmountTransaction(13000);
//        transaction1.setDateTimeTransmission(new Date());
//        transaction1.setTerminalID("2345678910");
//        transaction1.setCurrencyCode("040");
//        transaction1.setRecipientEmail("mulungojohnpaul@gmail.com");
//        transactionRepository.save(transaction1);
//
//        // ####################### ASSIGN ACCOUNTS TO USERS #######################################
//        this.assignAccountsToUsers();
//
//    }
//
//    private void assignAccountsToUsers(){
//        String[] emails = {
//                "betty.kirii@rafiki.com",
//                "bill.brandon@rafiki.com",
//                "anthony.muthuma@rafiki.com",
//                "rufusy.idachi@rafiki.com",
//        };
//        String[] accounts = {
//                "0714385056",
//                "0714385057",
//                "0714385058",
//                "0714385059"
//        };
//
//        for(int i=0; i<4; i++){
//            User user = userRepository.findByEmail(emails[i]);
//            Account account = accountRepository.findByAccountNumber(accounts[i]);
//            user.setUserAccount(account);
//            userRepository.save(user);
//        }
//    }
//}
//
