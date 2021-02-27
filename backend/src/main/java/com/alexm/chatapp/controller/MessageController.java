package com.alexm.chatapp.controller;


import com.alexm.chatapp.dao.MessageDAO;
import com.alexm.chatapp.dao.UserDAO;
import com.alexm.chatapp.entity.Message;
import com.alexm.chatapp.entity.MessageBody;
import com.alexm.chatapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    // Sends a message
    @PostMapping
    public ResponseEntity<String> send(@RequestBody MessageBody message) {
        User sender = userDAO.findById(message.getSenderId()), receiver = userDAO.findById(message.getReceiverId());
        if (message.getSenderId() <= 0 || sender == null)
            return new ResponseEntity<>("Bad sender id\n", HttpStatus.BAD_REQUEST);
        if (message.getReceiverId() <= 0 || receiver == null)
            return new ResponseEntity<>("Bad receiver id\n", HttpStatus.BAD_REQUEST);
        if (message.getBody().isEmpty())
            return new ResponseEntity<>("Message is empty\n", HttpStatus.BAD_REQUEST);

        messageDAO.add(new Message(sender, receiver, message.getBody(), new Timestamp(message.getDate())));
        return  new ResponseEntity<>( "Message sent", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Message>> getDiscussion(@RequestParam String senderName, @RequestParam String receiverName) {
        String currUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Allow only the user to access their own messages
        if (!currUser.equals(senderName) && !currUser.equals(receiverName)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        User sender = userDAO.findByUsername(senderName), receiver = userDAO.findByUsername(receiverName);
        ArrayList<Message> messages = (ArrayList<Message>)messageDAO.findDiscussion(sender, receiver);
        if (messages == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        for (Message msg : messages) {
            msg.getSender().setPassword(null);
            msg.getReceiver().setPassword(null);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
