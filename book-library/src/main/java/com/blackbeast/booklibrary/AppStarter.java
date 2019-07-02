package com.blackbeast.booklibrary;

import com.blackbeast.booklibrary.services.BookService;
import com.blackbeast.booklibrary.services.HireService;
import com.blackbeast.booklibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:custom.properties")
public class AppStarter implements CommandLineRunner {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    HireService hireService;

    @Override
    public void run(String... args) throws Exception {

    }

    /*public void init(){
        Book book = new Book("Ogniem i mieczem", 2000, "PWN", "78535635634", new Author("Henryk Sienkiewicz"));
        bookService.saveBook(book);

        Book book2 = new Book("Potop", 1990, "PWN", "90254385733", new Author("Henryk Sienkiewicz"));
        bookService.saveBook(book2);

        Book book3 = new Book("Pan Wołodyjowski", 1999, "PWN", "54671724546", new Author("Henryk Sienkiewicz"));
        bookService.saveBook(book3);
    }

    public void initUsers() {
        userService.createUser("admin", "pass");

        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("admin", "DEV");
        userService.addRoleToUser("admin", "USER");

        userService.createUser("user", "pass");
        userService.addRoleToUser("user", "USER");

        userService.createUser("dev", "pass");
        userService.addRoleToUser("dev", "DEV");
    }*/
}
