package rafikibora.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafikibora.services.UserServiceI;
import rafikibora.dto.UserDto;


@RestController
@RequestMapping("api/auth")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

   
//   @GetMapping("/me")
//   public ResponseEntity<String> me() {
//       return ResponseEntity.ok("GET is working yaay");
//   }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto user) {
        // log.info("----------------------");
        // log.info("The Data is:", user);
        // log.info("------------------------");
        userServiceI.save(user);
        return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
    }
//    @PostMapping("register")
//    public User save(@RequestBody User user){
//        try {
//            userServiceI.register(user);
//            System.out.println("success");
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
//
//        return user;
//    }
//
//    @PostMapping("login")
//    public boolean login(@RequestBody String email, String password){
//        boolean isLoggedIn = true;
//        try {
//            userServiceI.login(email, password);
//            System.out.println("success");
//            isLoggedIn = true;
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//            isLoggedIn = false;
//        }
//
//        return isLoggedIn;
//    }
//
//    @GetMapping("/user/get")
//    public @ResponseBody String get(){
//        return "users";
//    }
}

