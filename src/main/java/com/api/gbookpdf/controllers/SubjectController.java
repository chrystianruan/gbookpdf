package com.api.gbookpdf.controllers;

import com.api.gbookpdf.dtos.AuthorDTO;
import com.api.gbookpdf.dtos.SubjectDTO;
import com.api.gbookpdf.exceptions.AlreadyExistsException;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.services.AuthorService;
import com.api.gbookpdf.services.SubjectService;
import com.api.gbookpdf.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final Logger log = Logger.getLogger(SubjectController.class.getName());

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> create(@RequestBody SubjectDTO subjectDTO) {
        try {
            subjectService.addSubject(subjectDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build() ;
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno ao realizar cadastro de autor"));
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable String id, @RequestBody SubjectDTO subjectDTO) {
        try {
            subjectService.updateSubject(id, subjectDTO);
            return ResponseEntity.ok().build();
        } catch (EmptyException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtils.makeMessage(alreadyExistsException.getMessage()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.makeMessage("Erro interno ao realizar update de autor"));
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
