package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Integer> {

    List<User> findAll();
}
