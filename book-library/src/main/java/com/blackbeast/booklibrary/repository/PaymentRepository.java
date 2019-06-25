package com.blackbeast.booklibrary.repository;

import com.blackbeast.booklibrary.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT SUM(amount) FROM Payment p WHERE p.user.id=:id")
    BigDecimal getPaymentSumByUser(Integer id);

    List<Payment> findByUser_Id(Integer id);

    @Transactional
    Payment save(Payment payment);
}
