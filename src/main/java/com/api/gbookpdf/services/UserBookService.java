package com.api.gbookpdf.services;

import com.api.gbookpdf.dtos.UserBookDTO;
import com.api.gbookpdf.entities.Book;
import com.api.gbookpdf.entities.User;
import com.api.gbookpdf.entities.UserBook;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.repositories.BookRepository;
import com.api.gbookpdf.repositories.UserBookRepository;
import com.api.gbookpdf.repositories.UserRepository;
import com.api.gbookpdf.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserBookService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserBookRepository userBookRepository;

    public void storeUserBook(UserBookDTO userBookDTO) throws EmptyException {
        try {
            Long userId = Long.parseLong(HashUtils.decodeBase64(userBookDTO.getUserId()));
            Long bookId = Long.parseLong(HashUtils.decodeBase64(userBookDTO.getBookId()));
            if (!userRepository.existsById(userId)) {
                throw new EmptyException("Usuário");
            }
            if (!bookRepository.existsById(bookId)) {
                throw new EmptyException("Livro");
            }

            User user = userRepository.findById(userId).get();
            Book book = bookRepository.findById(bookId).get();
            UserBook userBook = new UserBook();
            userBook.setUser(user);
            userBook.setBook(book);
            userBook.setCreatedAt(new Date());
            userBook.setUpdatedAt(new Date());

            userBookRepository.save(userBook);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
