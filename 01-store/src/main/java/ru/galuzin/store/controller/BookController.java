package ru.galuzin.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.galuzin.store.domain.Book;
import ru.galuzin.store.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    BookService bookService;

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @GetMapping(value="/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        log.info("get book request");
        return bookService.getOne(id);
    }

    @RequestMapping (value="/add", method=RequestMethod.POST)
    public Book addBook(@RequestBody Book book) {
        return bookService.save(book);
    }
}
