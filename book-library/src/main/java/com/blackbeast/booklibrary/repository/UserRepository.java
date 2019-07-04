package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Role;
import com.blackbeast.booklibrary.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveUser(User user) {
        if(user != null) {
            boolean userExists = getUser(user.getUsername()) != null;

            if(!userExists)
                em.persist(user);
            else
                em.merge(user);
        }
    }

    public User getUser(String username) {
        List<User> users = em.createQuery("from User u where u.username = :username", User.class)
                .setParameter("username", username).getResultList();

        if(users == null)
            return null;

        if(users.isEmpty())
            return null;

        return users.get(0);
    }

}
