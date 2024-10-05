package com.api.gbookpdf.services;

import com.api.gbookpdf.dtos.BookDTO;
import com.api.gbookpdf.entities.Book;
import com.api.gbookpdf.exceptions.AlreadyExistsException;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.exceptions.ListEmptyException;
import com.api.gbookpdf.repositories.BookRepository;
import com.api.gbookpdf.utils.HashUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final String model = "Livro";
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void createBook(BookDTO bookDTO) throws AlreadyExistsException {
        if (bookRepository.existsByTitleAndAuthor(bookDTO.getTitle(), bookDTO.getAuthor().parseToObject())) {
            throw new AlreadyExistsException(model);
        }
        try {
            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor().parseToObject());
            book.setSubject(bookDTO.getSubject().parseToObject());
            book.setDescription(bookDTO.getDescription());
            bookRepository.save(book);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateBook(String hashId, BookDTO bookDTO) throws Exception {
        try {
            Long bookId = Long.parseLong(HashUtils.decodeBase64(hashId));
            if (!bookRepository.existsById(bookId)) {
                throw new EmptyException(model);
            }
            if (bookRepository.existsByTitleAndAuthorAndBookDifferent(bookDTO.getTitle(), bookDTO.getAuthor().parseToObject(), bookRepository.findOne(bookId))) {
                throw new AlreadyExistsException(model);
            }
            Book book = bookRepository.findOne(bookId);
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor().parseToObject());
            book.setDescription(bookDTO.getDescription());
            book.setSubject(bookDTO.getSubject().parseToObject());
            bookRepository.save(book);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<BookDTO> getAllBooks() throws ListEmptyException {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new ListEmptyException(model);
        }
        return this.generateListOfDTO(books);
    }

    public List<BookDTO> generateListOfDTO(List<Book> books) {
        List<BookDTO> bookDTOS = new ArrayList<>();
        try {
            for (Book book : books) {
                BookDTO bookDTO = book.toDTO();
                bookDTOS.add(bookDTO);
            }
            return bookDTOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public BookDTO getBook(String hashId) throws EmptyException {
        Long bookId = Long.parseLong(HashUtils.decodeBase64(hashId));
        if (!bookRepository.existsById(bookId)) {
            throw new EmptyException(model);
        }
        return bookRepository.findOne(bookId).toDTO();
    }

    public void deleteBook(String hashId) throws EmptyException {
        Long bookId = Long.parseLong(HashUtils.decodeBase64(hashId));
        if (!bookRepository.existsById(bookId)) {
            throw new EmptyException(model);
        }
        bookRepository.deleteById(bookId);
    }
}
