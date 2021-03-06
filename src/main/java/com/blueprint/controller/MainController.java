package com.blueprint.controller;

import com.blueprint.model.User;
import com.blueprint.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
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
@EnableJms
public class MainController {

    // option2: log4j2 (log4j2.xml)
    private static Logger logger = LogManager.getLogger(MainController.class);

    @Autowired
    private LogBuilder logBuilder;

    @Autowired
    KafkaTemplate<String,User> kafkaTemplate;//for kafka

    @Autowired
    private JmsTemplate jmsTemplate;//for IBM MQ

    @Autowired
    private UserService userService;

    private static final String template = "Hello, %s!";

    @GetMapping("/user")
    public User User(HttpServletRequest request,
                     @RequestParam(value = "name", defaultValue = "World")String name,
                     @RequestParam(value = "phoneNumber", defaultValue = "0")int phoneNumber){
        // initialize LogBuilder for this request.
        logBuilder.initRequest(request);
//        kafkaTemplate.send("Add new user",new User());
        User user = new User(String.format(template,name), phoneNumber);
        User newUser = userService.newUser(user);
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

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
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

    @GetMapping("send")
    String send(){
        try{
            jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World!");
            return "OK";
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }
}
