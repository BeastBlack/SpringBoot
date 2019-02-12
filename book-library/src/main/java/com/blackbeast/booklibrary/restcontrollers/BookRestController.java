package com.blackbeast.booklibrary.restcontrollers;

import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRestController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> books = bookService.getBooks();

        if(books == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/books/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeBook(@PathVariable("id") Integer id){
        Book book = bookService.getBook(id);

        if(book == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        bookService.removeBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addBook(@RequestBody Book book){
        if(book == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/edit/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> editBook(@PathVariable("id") Integer id, @RequestBody Book book){
        Book updateBook = bookService.getBook(id);

        if(updateBook == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(book == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        book.setId(updateBook.getId());
        book.getAuthor().setId(updateBook.getAuthor().getId());

        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") Integer id){
        Book book = bookService.getBook(id);

        if(book == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
