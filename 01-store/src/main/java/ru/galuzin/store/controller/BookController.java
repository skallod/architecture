package ru.galuzin.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.galuzin.store.domain.Book;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @GetMapping(value="/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        return new Book();
    }

}
