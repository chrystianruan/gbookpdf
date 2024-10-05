package com.api.gbookpdf.services;

import com.api.gbookpdf.entities.Book;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.dtos.AuthorDTO;
import com.api.gbookpdf.entities.Author;
import com.api.gbookpdf.repositories.AuthorRepository;
import com.api.gbookpdf.repositories.BookRepository;
import com.api.gbookpdf.utils.HashUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void addAuthor(AuthorDTO author) {
        try {
            Author authorSave = new Author();
            authorSave.setName(author.getName());
            authorRepository.save(authorSave);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateAuthor(String hashId, AuthorDTO authorDTO) throws Exception {
        try {
            Long authorId = Long.parseLong(HashUtils.decodeBase64(hashId));
            if (!authorRepository.existsById(authorId)) {
                throw new EmptyException("Autor não encontrado");
            }
            Author author = authorRepository.findOne(authorId);
            author.setName(authorDTO.getName());
            authorRepository.save(author);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void deleteAuthor(String id) throws EmptyException {
        Long authorId = Long.parseLong(HashUtils.decodeBase64(id));
        if (!authorRepository.existsById(authorId)) {
            throw new EmptyException("Autor não encontrado");
        }
        for (Book book : bookRepository.findAllByAuthor(authorRepository.findOne(authorId))) {
            book.setAuthor(authorRepository.findOne((Long.valueOf(1))));
            bookRepository.save(book);
        }
        authorRepository.deleteById(authorId);
    }

    public void deleteAuthorAndBooks(AuthorDTO authorDTO) throws EmptyException {
        Long authorId = Long.parseLong(HashUtils.decodeBase64(authorDTO.getId()));
        if (!authorRepository.existsById(authorId)) {
            throw new EmptyException("Autor não encontrado");
        }
        bookRepository.deleteAll(bookRepository.findAllByAuthor(authorRepository.findOne(authorId)));
        authorRepository.deleteById(authorId);

    }

    public List<AuthorDTO> list() throws EmptyException {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            throw new EmptyException("Nenhum autor encontrado");
        }
        return generateListOfDTO(authors);
    }

    public List<AuthorDTO> generateListOfDTO(List<Author> authors) {
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        for (Author author : authors) {
            AuthorDTO authorDTO = author.parseToDTO();
            authorDTOS.add(authorDTO);
        }
        return authorDTOS;
    }
    public AuthorDTO showAuthor(String id) throws EmptyException {
        Long authorId = (Long) Long.parseLong(HashUtils.decodeBase64(id));
        if (!authorRepository.existsById(authorId)) {
            throw new EmptyException("Autor não encontrado");
        }

        return authorRepository.findOne(authorId).parseToDTO();
    }


}
