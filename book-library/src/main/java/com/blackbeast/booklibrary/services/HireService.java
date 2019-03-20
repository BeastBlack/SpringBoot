package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.repository.HireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HireService {

    @Autowired
    HireRepository hireRepository;

    public List<Hire> getHiresByBookId(Integer id) {
        return hireRepository.findByHiredBook_Id(id);
    }
}
