package com.blackbeast.booklibrary;

import com.blackbeast.booklibrary.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStarter implements CommandLineRunner {

    @Autowired
    Book book;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(book);
    }
}
