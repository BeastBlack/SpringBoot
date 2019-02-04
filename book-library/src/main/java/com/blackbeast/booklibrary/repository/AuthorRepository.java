package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    public Author getAuthor(int id){
        return em.find(Author.class, id);
    }

    @Transactional
    public void saveAuthor(Author author){
        if(author != null)
            em.persist(author);
    }

    @Transactional
    public void updateAuthor(Author author){
        if(author != null)
            em.merge(author);
    }

    @Transactional
    public void removeAuthor(Author author) {
        if(author != null)
            em.remove(author);
    }
}
