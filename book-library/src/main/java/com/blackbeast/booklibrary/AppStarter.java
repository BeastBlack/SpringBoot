package com.blackbeast.booklibrary;

import com.blackbeast.booklibrary.domain.Author;
import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:custom.properties")
public class AppStarter implements CommandLineRunner {

    @Autowired
    BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    public void init(){
        Book book = new Book("Ogniem i mieczem", 2000, "PWN", "78535635634", new Author("Henryk Sieniewicz"));
        bookService.saveBook(book);

        Book book2 = new Book("Potop", 1990, "PWN", "90254385733", new Author("Henryk Sieniewicz"));
        bookService.saveBook(book2);

        Book book3 = new Book("Pan Wołodyjowski", 1999, "PWN", "54671724546", new Author("Henryk Sieniewicz"));
        bookService.saveBook(book3);
    }
}
