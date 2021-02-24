package com.alexm.chatapp.dao;

import com.alexm.chatapp.entity.Message;
import com.alexm.chatapp.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Message message) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(message);
    }

    @Override
    @Transactional
    public List<Message> findBySender(User sender) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Message m where m.sender = :sender");
        q.setParameter("sender", sender.getId());
        List<Message> res = q.getResultList();
        return (!res.isEmpty()) ? res : null;
    }

    @Override
    @Transactional
    public List<Message> findByReceiver(User receiver) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Message m where m.receiver = :receiver");
        q.setParameter("receiver", receiver);
        List<Message> res = q.getResultList();
        return (!res.isEmpty()) ? res : null;
    }
}
