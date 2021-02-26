package com.alexm.chatapp.dao;

import com.alexm.chatapp.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(User user) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(user);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from User u");
        List<User> list = q.getResultList();
        return (!list.isEmpty()) ? list : null;
    }

    @Override
    @Transactional
    public User findByUsername(String name) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from User u where u.username=:name");
        q.setParameter("name", name);
        List<User> list = q.getResultList();
        return (!list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional
    public User findById(Long id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from User u where u.id=:id");
        q.setParameter("id", id);
        List<User> list = q.getResultList();
        return (!list.isEmpty()) ? list.get(0) : null;
    }

}
