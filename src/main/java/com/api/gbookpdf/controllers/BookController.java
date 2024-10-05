package com.api.gbookpdf.controllers;

import com.api.gbookpdf.dtos.BookDTO;
import com.api.gbookpdf.exceptions.AlreadyExistsException;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.exceptions.ListEmptyException;
import com.api.gbookpdf.services.BookService;
import com.api.gbookpdf.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequestMapping("/books")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    private final Logger log = Logger.getLogger(BookController.class.getName());
    @GetMapping
    public ResponseEntity<Map<String, ?>> books() {
        try {
            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(bookService.getAllBooks()));
        } catch (ListEmptyException listEmptyException) {
            return ResponseEntity.status(listEmptyException.getStatus()).body(ResponseUtils.makeMessage(listEmptyException.getMessage()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno"));
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> create(@RequestBody BookDTO book) {
        try {
            bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(alreadyExistsException.getStatus()).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (RuntimeException e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno"));
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@RequestBody BookDTO book, @PathVariable String id) {
        try {
            bookService.updateBook(id, book);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EmptyException | AlreadyExistsException customException) {
            return ResponseEntity.status(customException.getStatus()).body(ResponseUtils.makeMessage(customException.getMessage()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage(e.getMessage()));
        }
    }
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (EmptyException e) {
            return ResponseEntity.status(e.getStatus()).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage(e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, ?>> show(@PathVariable String id) {
        try {
            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(bookService.getBook(id)));
        } catch (EmptyException e) {
            return ResponseEntity.status(e.getStatus()).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage(e.getMessage()));
        }
    }
}
