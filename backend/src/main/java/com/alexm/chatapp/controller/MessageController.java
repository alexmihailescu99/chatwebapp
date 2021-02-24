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
public class MessageController {
    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/send")
    public ResponseEntity<String> send(MessageBody message) {
        StringBuilder responseMessage = new StringBuilder();
        User sender = userDAO.findById(message.getSenderId()), receiver = userDAO.findById(message.getReceiverId());
        if (message.getSenderId() <= 0) responseMessage.append("Wrong sender id\n");
        if (message.getReceiverId() <= 0) responseMessage.append("Wrong receiver id\n");
        if (message.getBody().isEmpty()) responseMessage.append("Message is empty\n");
        if (sender == null) responseMessage.append("Sender does not exist\n");
        if (receiver == null) responseMessage.append("Receiver does not exist\n");
        // If there were no errors
        if (responseMessage.length() == 0) {
            messageDAO.add(new Message(sender, receiver, message.getBody(), new Timestamp(message.getDate())));
        }
        return (responseMessage.length() == 0) ? new ResponseEntity<>( "Message sent", HttpStatus.OK)
                : new ResponseEntity<>(responseMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ArrayList<Message>> getUserMessages(@PathVariable @Param("name") String username) {
        String currUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Allow only the user to access their own messages
        if (!currUser.equals(username)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        User user = userDAO.findByUsername(username);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        ArrayList<Message> messages = (ArrayList<Message>)messageDAO.findByReceiver(user);
        if (messages == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        for (Message msg : messages) {
            msg.getSender().setPassword(null);
            msg.getReceiver().setPassword(null);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}