package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    Role save(Role role);

    @Transactional
    @Modifying
    @Query("DELETE FROM Role WHERE user_Id = :id AND name = :name")
    void deleteByUserIdAndName(@Param("id") Integer id, @Param("name") String name);
}
