package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Hire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
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

    @Transactional
    Hire save(Hire hire);

    @Query("SELECT h FROM Hire h WHERE h.realGiveBackDate IS NULL AND h.hiredBook.id=:bookId")
    List<Hire> findHireByIdAndNotGiveBack(@Param("bookId") Integer id);

    List<Hire> findByHireUser_Id(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Hire SET realGiveBackDate = CURRENT_TIME WHERE id=:hireId AND realGiveBackDate IS NULL")
    void setHireAsGiveBack(@Param("hireId") Long id);

    @Query("SELECT h FROM Hire h WHERE h.realGiveBackDate IS NULL")
    List<Hire> findHiresNotGiveBack();

    @Query("SELECT h FROM Hire h WHERE h.realGiveBackDate > h.plannedGiveBackDate AND h.realGiveBackDate IS NOT NULL AND h.hireUser.id=:id")
    List<Hire> findHiresExpiredByUserId(Integer id);
}
