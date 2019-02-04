package com.blackbeast.booklibrary.controllers;

import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String getBooks(Model model){
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @RequestMapping(value = "/books/delete/{id}", method = RequestMethod.GET)
    public String removeBook(@PathVariable("id") Integer id){
        bookService.removeBook(id);
        return "redirect:/books";
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.GET)
    public String addBook(Model model){
        Book book = bookService.getNewBook();
        model.addAttribute("book", book);
        return "book";
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public String saveBook(@Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book";
        }else {
            bookService.saveBook(book);
            return "redirect:/books";
        }
    }

    @RequestMapping(value = "/books/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Integer id, Model model){
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "book";
    }
}
