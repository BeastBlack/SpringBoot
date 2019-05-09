package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.repository.BookRepository;
import com.blackbeast.booklibrary.repository.HireRepository;
import com.blackbeast.booklibrary.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@PropertySource("classpath:custom.properties")
public class HireService {

    @Autowired
    HireRepository hireRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;

    @Value("${library.hire.giveBackDays}")
    Integer giveBackDays;

    public List<Hire> getHiresByBookId(Integer id) {
        return hireRepository.findByHiredBook_Id(id);
    }

    public Hire hire(Integer bookId) {
        boolean isBookAvailable = hireRepository.findBookByIdAndNotGiveBack(bookId).isEmpty();

        if(isBookAvailable) {
            Book book = bookRepository.getBook(bookId);
            User user = userService.getLoggedUser();

            if(book != null && user != null){
                Hire hire = new Hire();
                hire.setHiredBook(book);
                hire.setHireUser(user);

                Date hireDate = new Date();
                Date plannedGiveBackDate = DateUtils.addDaysToDate(hireDate, giveBackDays);

                hire.setHireDate(hireDate);
                hire.setPlannedGiveBackDate(plannedGiveBackDate);

                hireRepository.save(hire);
                return hire;
            }
        }

        return null;
    }

    public List<Hire> getHireListByUserId(Integer id) {
        return hireRepository.findByHireUser_Id(id);
    }

    public void setHireAsGiveBack(Long id) {
        hireRepository.setHireAsGiveBack(id);
    }

    public List<Hire> getHireListNotGiveBack() {
        return hireRepository.findBookNotGiveBack();
    }

    public Hire getHireById(Long id) {
        return hireRepository.findById(id).get();
    }
}
