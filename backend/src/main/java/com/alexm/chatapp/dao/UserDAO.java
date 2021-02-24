package com.alexm.chatapp.dao;

import com.alexm.chatapp.entity.User;

public interface UserDAO {
    public void add(User user);
    public User findByUsername(String name);
    public User findById(Long id);
}
