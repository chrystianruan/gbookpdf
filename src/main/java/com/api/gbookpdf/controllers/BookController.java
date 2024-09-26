package com.api.gbookpdf.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {
    @GetMapping
    public List<String> books() {
        List<String> books = new ArrayList<>();
        books.add("Book 1");
        books.add("Book 2");
        books.add("Book 3");

        return books;

    }
}
