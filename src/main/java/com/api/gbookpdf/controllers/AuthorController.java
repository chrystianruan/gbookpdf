package com.api.gbookpdf.controllers;

import com.api.gbookpdf.dtos.AuthorDTO;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.repositories.AuthorRepository;
import com.api.gbookpdf.services.AuthorService;
import com.api.gbookpdf.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final Logger log = Logger.getLogger(AuthorController.class.getName());

    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> create(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.addAuthor(authorDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtils.makeMessage("Autor adicionado com sucesso"));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar cadastro de autor"));
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable String id, @RequestBody AuthorDTO authorDTO) {
        try {
            authorService.updateAuthor(id, authorDTO);
            return ResponseEntity.ok(ResponseUtils.makeMessage("Autor atualizado com sucesso"));
        } catch (EmptyException e) {
            return ResponseEntity.status(e.getStatus()).body(ResponseUtils.makeMessage(e.getMessage()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar update de autor"));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, ?>> get(@PathVariable String id) {
        try {
            AuthorDTO authorDTO = authorService.showAuthor(id);

            return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(authorDTO));
        } catch (EmptyException emptyException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.makeMessage("Autor não encontrado"));
        }
    }

    @GetMapping()
    public ResponseEntity<Map<String, ?>> getAll() {
        try {
            List<AuthorDTO> list = authorService.list();

            return ResponseEntity.ok(ResponseUtils.makeMessageWithList(list));
        } catch (EmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao buscar autores"));
        }

    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Map<String, ?>> delete(@PathVariable String id) {
        try {
            authorService.deleteAuthor(id);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (EmptyException emptyException) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao realizar update de autor"));
        }

    }
}
