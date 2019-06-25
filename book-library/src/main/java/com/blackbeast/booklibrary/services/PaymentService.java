package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.domain.Payment;
import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.repository.HireRepository;
import com.blackbeast.booklibrary.repository.PaymentRepository;
import com.blackbeast.booklibrary.repository.UserRepositoryJpa;
import com.blackbeast.booklibrary.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    HireRepository hireRepository;

    @Autowired
    UserRepositoryJpa userRepository;

    public BigDecimal getPaymentSumByUser(Integer userId) {
        BigDecimal payment = paymentRepository.sumPaymentByUser(userId);

        return payment != null ? payment : new BigDecimal(0);
    }

    public BigDecimal getPenaltySumByUser(Integer userId) {
        List<Hire> hires = hireRepository.findExpiredHiresByUser(userId);

        BigDecimal penaltySum = new BigDecimal(0);

        for(Hire hire : hires){
            BigDecimal hirePenalty = hire.getDailyPenalty()
                    .multiply(new BigDecimal(
                            DateUtils.daysDiff(
                                    hire.getRealGiveBackDate(), hire.getPlannedGiveBackDate())));

            penaltySum = penaltySum.add(hirePenalty);
        }

        return penaltySum;
    }

    public Map<User, BigDecimal> getUsersWithNegativeBalance() {
        Map<User, BigDecimal> userMap = new HashMap<>();

        List<User> users = userRepository.findAll();

        for(User user : users){
            BigDecimal balance = getPaymentSumByUser(user.getId()).
                    subtract(getPenaltySumByUser(user.getId()));

            if(balance.compareTo(BigDecimal.ZERO) < 0)
                userMap.put(user, balance);
        }

        return userMap;
    }

    public void pay(Integer userId) {
        Payment payment = new Payment();

        User user = userRepository.getOne(userId);

        payment.setUser(user);

        BigDecimal amount = getPenaltySumByUser(user.getId()).
                subtract(getPaymentSumByUser(user.getId()));

        payment.setAmount(amount);
        payment.setDate(new Date());
        paymentRepository.save(payment);
    }
}
