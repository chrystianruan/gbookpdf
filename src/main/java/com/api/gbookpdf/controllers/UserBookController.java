package com.api.gbookpdf.controllers;

import com.api.gbookpdf.dtos.UserBookDTO;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.services.UserBookService;
import com.api.gbookpdf.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserBookController {
    @Autowired
    private UserBookService userBookService;
    @PostMapping("/mark-book")
    public ResponseEntity<Map<String, String>> storeActionUserBook(@RequestBody UserBookDTO userBookDTO) {
        try {
            userBookService.storeUserBook(userBookDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EmptyException e) {
            return ResponseEntity.status(e.getStatus()).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno"));
        }
    }
}
