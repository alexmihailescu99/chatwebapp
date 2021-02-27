package com.alexm.chatapp.controller;

import com.alexm.chatapp.dao.UserDAO;
import com.alexm.chatapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // Find all users
    @GetMapping
    public ResponseEntity<ArrayList<User>> getUsers() {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = userDAO.findAll();
        for (User u : users) {
            u.setPassword(null);
        }
        return new ResponseEntity<>((ArrayList<User>)users, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testUser() {
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        // This should be checked on the front-end, but double checking can't hurt
        if (user.getUsername().length() < 4)
            return new ResponseEntity<>("User should be at least 4 characters\n", HttpStatus.BAD_REQUEST);

        if (user.getPassword().length() < 8)
            return new ResponseEntity<>("Password should have at least 8 characters\n", HttpStatus.BAD_REQUEST);

        if (user.getFullName().length() < 6)
            return new ResponseEntity<>("Full name is too short\n", HttpStatus.BAD_REQUEST);

        // If the user exists, notify the front-end
        if (userDAO.findByUsername(user.getUsername()) != null)
            return new ResponseEntity<String>("User already exists", HttpStatus.BAD_REQUEST);

        // Encrypt the password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.add(user);
        return new ResponseEntity<>(user.getUsername() + " added", HttpStatus.OK);
    }

}
