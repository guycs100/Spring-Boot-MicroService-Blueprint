package com.blueprint.controller;

import com.blueprint.model.User;
import com.blueprint.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.blueprint.log.LogBuilder;

import java.util.Optional;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Slf4j  // option1: Slf4j using lombok (log4j2.xml)
@RestController
@RequestMapping("/app")
public class MainController {

    // option2: log4j2 (log4j2.xml)
    private static Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private LogBuilder logBuilder;

    private static final String template = "Hello, %s!";
//    @Transactional(timeout = 1)
    @GetMapping("/user")
    public User User(HttpServletRequest request,
                     @RequestParam(value = "name", defaultValue = "World")String name,
                     @RequestParam(value = "phoneNumber", defaultValue = "0")int phoneNumber){
        System.out.println(System.currentTimeMillis());
        // initialize LogBuilder for this request.
        logBuilder.initRequest(request);

//        int i = 1;
//        while (i < 100000) {
//            System.out.println(i);
//            i++;
//        }
//        System.out.println(System.currentTimeMillis());
        User user = new User(String.format(template,name), phoneNumber);
        User newUser = UserService.newUser(user);
        // Slf4j example with the help of lombok - simple
        log.info("test message");

        // Slf4j example with the help of lombok - using LogBuilder
        // LogBuilder message generation is always being invoked
        log.error(logBuilder.get().setMessage("test LogBuilder").toString());

        // Log4j2 example - using LogBuilder + lambda
        logger.debug(()-> {
            // invoked only when log level == DEBUG
            return logBuilder.get().setMessage("test LogBuilder + lambda").toString();
        });
        return newUser;
    }
//    @PostMapping("/user")
//    public User User(@RequestBody User newUser){
//        System.out.println(System.currentTimeMillis());
////        int i = 1;
////        while (i < 100000) {
////            System.out.println(i);
////            i++;
////        }
////        System.out.println(System.currentTimeMillis());
//        User user = UserService.newUser(newUser);
//        return user;
//    }
    @GetMapping("/user/{id}")
    Optional<User> getUserById(@PathVariable Long id) {
        Optional<User> user = UserService.getUserById(id);

        return user;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<String> getFoobar() throws InterruptedException {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(8000); //this will cause a timeout
                return "foobar";
            }
        };
    }
    @PostConstruct
    void printOnce(){
        System.out.println("Print Once");
    }
}
