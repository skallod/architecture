package ru.galuzin.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.galuzin.store.domain.Book;
import ru.galuzin.store.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();

        List<Book> activeBookList = new ArrayList<>();

        for (Book book : bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public Book findOne(Long id) {
        return bookRepository.findById(id).get();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findByTitleContaining(String keyword) {
        List<Book> bookList = bookRepository.findByTitleContaining(keyword);

        List<Book> activeBookList = new ArrayList<>();

        for (Book book : bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public void removeOne(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getOne(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

}
