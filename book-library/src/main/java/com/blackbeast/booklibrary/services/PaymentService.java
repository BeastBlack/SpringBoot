package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.repository.HireRepository;
import com.blackbeast.booklibrary.repository.PaymentRepository;
import com.blackbeast.booklibrary.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@PropertySource("classpath:custom.properties")
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    HireRepository hireRepository;

    @Value("${library.hire.dailyPenalty}")
    BigDecimal dailyPenalty;

    public BigDecimal getPaymentSumByUser(Integer id) {
        BigDecimal payment = paymentRepository.getPaymentSumByUser(id);
        return payment != null ? payment : new BigDecimal(0);
    }

    public BigDecimal getPentalySumByUser(Integer id) {
        List<Hire> hires = hireRepository.findHiresExpiredByUserId(id);

        BigDecimal penaltySum = new BigDecimal(0);

        for(Hire hire : hires){
            Long daysExpireCount = DateUtils.daysDiff(hire.getRealGiveBackDate(), hire.getPlannedGiveBackDate());
            BigDecimal hirePenalty = dailyPenalty.multiply(new BigDecimal(daysExpireCount));
            penaltySum = penaltySum.add(hirePenalty);
        }

        return penaltySum;
    }
}
