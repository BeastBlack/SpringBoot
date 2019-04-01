package com.blackbeast.booklibrary.controllers;

import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.services.BookService;
import com.blackbeast.booklibrary.services.HireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HireController {

    @Autowired
    BookService bookService;

    @Autowired
    HireService hireService;

    @RequestMapping(value = "/books/hires/{id}", method = RequestMethod.GET)
    public String getHires(Model model, @PathVariable("id") Integer id){
        Book book = bookService.getBook(id);
        List<Hire> hires = hireService.getHiresByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("hires", hires);
        return "hires";
    }

    @RequestMapping(value = "/books/hire/{id}", method = RequestMethod.GET)
    public String hire(@PathVariable("id") Integer id){
        hireService.save(id);
        return "redirect:/books";
    }
}
