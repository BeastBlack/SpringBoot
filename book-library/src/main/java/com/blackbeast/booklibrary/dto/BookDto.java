package com.blackbeast.booklibrary.dto;

import java.util.Date;

public class BookDto {
    private Integer id;
    private String title;
    private Integer year;
    private String publisher;
    private String isbn;
    private String author;
    private boolean hireStatus;
    private Date hiredTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isHireStatus() {
        return hireStatus;
    }

    public void setHireStatus(boolean hireStatus) {
        this.hireStatus = hireStatus;
    }

    public Date getHiredTo() {
        return hiredTo;
    }

    public void setHiredTo(Date hiredTo) {
        this.hiredTo = hiredTo;
    }
}
