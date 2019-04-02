package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Hire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface HireRepository extends JpaRepository<Hire, Long> {

    @Override
    List<Hire> findAll();

    @Override
    Optional<Hire> findById(Long aLong);

    List<Hire> findByHiredBook_Id(Integer id);

    @Query("SELECT h FROM Hire h WHERE h.realGiveBackDate IS NULL AND h.hiredBook.id=:bookId")
    List<Hire> findByHiredBookIdNotGiveBack(@Param("bookId") Integer id);

    @Transactional
    Hire save(Hire hire);
}
