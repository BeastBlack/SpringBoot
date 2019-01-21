package com.blackbeast.booklibrary;

import com.blackbeast.booklibrary.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:custom.properties")
public class AppStarter implements CommandLineRunner {

    /*@Autowired
    Book book;

    Book book2;

    @Value("${spring.pagesize:25}")
    Integer size;*/

    @Override
    public void run(String... args) throws Exception {

    }

    /*@Autowired
    public void setBook2(Book book2) {
        this.book2 = book2;
    }*/
}
