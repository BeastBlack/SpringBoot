package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Hire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface HireRepository extends JpaRepository<Hire, Long> {

    @Query("SELECT h FROM Hire h WHERE h.hiredBook.id=:bookId")
    @Transactional
    public List<Hire> findByBookId(@Param("bookId") Integer bookId);
}
