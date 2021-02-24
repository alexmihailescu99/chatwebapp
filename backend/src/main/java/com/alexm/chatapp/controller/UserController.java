package com.alexm.chatapp.controller;

import com.alexm.chatapp.dao.UserDAO;
import com.alexm.chatapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello from the back-end", HttpStatus.OK);
    }

    @GetMapping("/testUser")
    public ResponseEntity<String> testUser() {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(User user) {
        StringBuilder responseMessage = new StringBuilder();
        // Build the errors
        // This should be checked on the front-end, but double checking can't hurt
        if (user.getUsername().length() < 4) responseMessage.append("User should be at least 4 characters\n");
        if (user.getPassword().length() < 8) responseMessage.append("Password should have at least 8 characters\n");
        if (user.getFullName().length() < 6) responseMessage.append("Full name is too short\n");
        // If the user exists, notify the front-end
        if (userDAO.findByUsername(user.getUsername()) != null) responseMessage.append("User already exists\n");
        // Encrypt the password
        if (responseMessage.length() == 0) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDAO.add(user);
        }
        // If the string builder is empty, user is correct, else return bad request
        return (responseMessage.length() == 0) ? new ResponseEntity<>(user.getUsername() + " added", HttpStatus.OK)
            : new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }

}
