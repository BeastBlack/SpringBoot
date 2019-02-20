package com.blackbeast.booklibrary.restcontrollers;

import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @RequestMapping(value = "/books/getByAuthor", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam(name = "author", required = true) String authorName){
        List<Book> books = bookService.getBooksByAuthor(authorName);

        if(books == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/books/get", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "year", required = false) Integer year,
                                               @RequestParam(value = "publisher", required = false) String publisher,
                                               @RequestParam(value = "isbn", required = false) String isbn){
        List<Book> books = bookService.getBooks(year, publisher, isbn);

        if(books == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/books/getByTitle", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam(name = "title", required = true) String title){
        List<Book> books = bookService.getBooksByTitle(title);

        if(books == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDetails> exceptionHandler(MissingServletRequestParameterException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(ex.getClass().getSimpleName(), ex.getMessage());

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
