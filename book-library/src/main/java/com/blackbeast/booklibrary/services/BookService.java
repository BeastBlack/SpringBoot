package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Author;
import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.domain.Hire;
import com.blackbeast.booklibrary.dto.BookDto;
import com.blackbeast.booklibrary.repository.AuthorRepository;
import com.blackbeast.booklibrary.repository.BookRepository;
import com.blackbeast.booklibrary.repository.HireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    HireRepository hireRepository;

    public List<Book> getBooks(){
        return new ArrayList<>(bookRepository.getBooks());
    }

    public void saveBook(Book book){
        if(book != null){
            System.out.println("Zapisuje książkę o id: " + book.getId());
            boolean bookExists = bookRepository.getBook(book.getId()) != null;
            System.out.println(bookExists);

            if(bookExists) {
                authorRepository.updateAuthor(book.getAuthor());
                bookRepository.updateBook(book);
            }else {
                authorRepository.saveAuthor(book.getAuthor());
                bookRepository.saveBook(book);
            }
        }
    }

    public void removeBook(int id){
        Book bookToRemove = bookRepository.getBook(id);
        Author authorToRemove = bookToRemove.getAuthor();

        bookRepository.removeBook(bookToRemove);
        authorRepository.removeAuthor(authorToRemove);
    }

    public Book getNewBook(){
        Book newBook = new Book();
        newBook.setAuthor(new Author());
        return newBook;
    }

    public Book getBook(int id){
        return bookRepository.getBook(id);
    }

    public List<Book> getBooksByAuthor(String authorName) {
        if(authorName != null)
            return new ArrayList(bookRepository.getBooksByAuthor(authorName));
        else
            return null;
    }

    public List<Book> getBooks(Integer year, String publisher, String isbn) {
        return new ArrayList<>(bookRepository.getBooks(year, publisher, isbn));
    }

    public List<Book> getBooksByTitle(String title) {
        if(title != null)
            return new ArrayList(bookRepository.getBooksByTitle(title));
        else
            return null;
    }

    public BookDto convert(Book book) {
        if(book == null)
            return null;

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setYear(book.getYear());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAuthorName(book.getAuthor().getName());

        List<Hire> hires = hireRepository.findBookByIdAndNotGiveBack(book.getId());

        bookDto.setHireStatus(hires.size() > 0);

        if(hires.size() > 0)
            bookDto.setGiveBackDate(hires.get(0).getPlannedGiveBackDate());

        return bookDto;
    }

    public List<BookDto> convert(List<Book> books) {
        if(books == null)
            return null;

        List<BookDto> bookDtoList = new ArrayList<>();

        for(Book book : books)
            bookDtoList.add(convert(book));

        return bookDtoList;
    }
}
