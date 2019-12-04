package ru.galuzin.store.repository;


import org.springframework.data.repository.CrudRepository;
import ru.galuzin.store.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitleContaining(String keyword);
}
