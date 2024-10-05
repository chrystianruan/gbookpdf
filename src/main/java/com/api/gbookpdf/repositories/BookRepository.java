package com.api.gbookpdf.repositories;

import com.api.gbookpdf.entities.Author;
import com.api.gbookpdf.entities.Book;
import com.api.gbookpdf.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.id = ?1")
    Book findOne(Long id);

    @Query("select count(book.id) > 1 from Book book where book.title ilike concat('%', ?1, '%') and book.author = ?2")
    boolean existsByTitleAndAuthor(String title, Author author);

    @Query("select count(book.id) > 1 from Book book where book.title ilike concat('%', ?1, '%') and book.author = ?2")
    boolean existsByTitleAndAuthorAndBookDifferent(String title, Author author, Book book);

    List<Book> findAllByAuthor(Author author);
    List<Book> findAllBySubject(Subject subject);
}
