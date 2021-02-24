package com.alexm.chatapp.dao;

import com.alexm.chatapp.entity.Message;
import com.alexm.chatapp.entity.User;

import java.util.List;

public interface MessageDAO {
    public void add(Message message);
    public List<Message> findBySender(User sender);
    public List<Message> findByReceiver(User receiver);
}
