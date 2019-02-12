package com.blackbeast.booklibrary.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "Tytuł nie może być pusty")
    @Size(min=2, message = "Tytuł musi posiadać co najmniej 2 litery")
    private String title;

    @NotNull(message = "Rok wydania nie może być pusty")
    @Range(min=1, max=9999, message = "Rok wydania musi być z przedziału 1 - 9999")
    private Integer year;
    private String publisher;
    private String isbn;

    @OneToOne
    private Author author;

    public Book(){

    }

    public Book(String title, int year, String publisher, String isbn, Author author) {
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.isbn = isbn;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
