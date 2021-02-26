package com.alexm.chatapp.dao;

import com.alexm.chatapp.entity.User;

import java.util.List;

public interface UserDAO {
    public void add(User user);
    public List<User> findAll();
    public User findByUsername(String name);
    public User findById(Long id);
}
