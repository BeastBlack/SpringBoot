package com.blackbeast.booklibrary.domain;

import org.springframework.stereotype.Component;

@Component
public class Book {
    private String title;
    private int year;
    private String publisher;
    private String isbn;

    public Book(){
        this.title = "Ogniem i mieczem";
        this.year = 1972;
        this.publisher = "Wydawca XYZ";
        this.isbn = "AZ43434";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
