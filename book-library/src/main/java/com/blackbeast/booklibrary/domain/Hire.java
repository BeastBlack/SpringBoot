package com.blackbeast.booklibrary.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Hire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date hireDate;
    private Date plannedGiveBackDate;
    private Date realGiveBackDate;

    @OneToOne
    private User hireUser;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book hiredBook;

    private BigDecimal dailyPenalty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getPlannedGiveBackDate() {
        return plannedGiveBackDate;
    }

    public void setPlannedGiveBackDate(Date plannedGiveBackDate) {
        this.plannedGiveBackDate = plannedGiveBackDate;
    }

    public Date getRealGiveBackDate() {
        return realGiveBackDate;
    }

    public void setRealGiveBackDate(Date realGiveBackDate) {
        this.realGiveBackDate = realGiveBackDate;
    }

    public User getHireUser() {
        return hireUser;
    }

    public void setHireUser(User hireUser) {
        this.hireUser = hireUser;
    }

    public Book getHiredBook() {
        return hiredBook;
    }

    public void setHiredBook(Book hiredBook) {
        this.hiredBook = hiredBook;
    }

    public BigDecimal getDailyPenalty() {
        return dailyPenalty;
    }

    public void setDailyPenalty(BigDecimal dailyPenalty) {
        this.dailyPenalty = dailyPenalty;
    }
}
