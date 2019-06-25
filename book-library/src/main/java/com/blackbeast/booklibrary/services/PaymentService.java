package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.domain.Payment;
import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.repository.HireRepository;
import com.blackbeast.booklibrary.repository.PaymentRepository;
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
    UserService userService;

    public BigDecimal getPaymentSumByUser(Integer id) {
        BigDecimal payment = paymentRepository.getPaymentSumByUser(id);
        return payment != null ? payment : new BigDecimal(0);
    }

    public BigDecimal getPenaltySumByUser(Integer id) {
        List<Hire> hires = hireRepository.findHiresExpiredByUserId(id);

        BigDecimal penaltySum = new BigDecimal(0);

        for(Hire hire : hires){
            Long daysExpireCount = DateUtils.daysDiff(hire.getRealGiveBackDate(), hire.getPlannedGiveBackDate());
            BigDecimal hirePenalty = hire.getDailyPenalty().multiply(new BigDecimal(daysExpireCount));
            penaltySum = penaltySum.add(hirePenalty);
        }

        return penaltySum;
    }

    public void pay(Integer userId) {
        Payment payment = new Payment();
        payment.setUser(userService.getUser(userId));
        payment.setAmount(getPenaltySumByUser(userId));
        payment.setDate(new Date());
        paymentRepository.save(payment);
    }

    public Map<User, BigDecimal> getUsersWithNegativeSaldo() {
        Map<User, BigDecimal> usersMap = new HashMap<>();

        List<User> users = userService.getAll();

        for(User user : users) {
            BigDecimal balance = getPaymentSumByUser(user.getId()).
                    subtract(getPenaltySumByUser(user.getId()));

            if(balance.compareTo(BigDecimal.ZERO) < 0)
                usersMap.put(user, balance);
        }

        return usersMap;
    }
}
