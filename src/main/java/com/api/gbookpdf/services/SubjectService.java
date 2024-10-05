package com.api.gbookpdf.services;

import com.api.gbookpdf.dtos.AuthorDTO;
import com.api.gbookpdf.dtos.SubjectDTO;
import com.api.gbookpdf.entities.Book;
import com.api.gbookpdf.entities.Subject;
import com.api.gbookpdf.exceptions.AlreadyExistsException;
import com.api.gbookpdf.exceptions.EmptyException;
import com.api.gbookpdf.exceptions.ListEmptyException;
import com.api.gbookpdf.repositories.AuthorRepository;
import com.api.gbookpdf.repositories.BookRepository;
import com.api.gbookpdf.repositories.SubjectRepository;
import com.api.gbookpdf.utils.HashUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private BookRepository bookRepository;
    private final String model = "Assunto";

    @Transactional
    public void addSubject(SubjectDTO subjectDTO) throws AlreadyExistsException {
        try {
            if (subjectRepository.existsByName(subjectDTO.getName())) {
                throw new AlreadyExistsException(model);
            }

            Subject subject = new Subject();
            subject.setName(subjectDTO.getName());
            subjectRepository.save(subject);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateSubject(String hashId, SubjectDTO subjectDTO) throws Exception {
        try {
            Long subjectId = Long.parseLong(HashUtils.decodeBase64(hashId));
            if (!subjectRepository.existsById(subjectId)) {
                throw new EmptyException(model);
            }
            if (subjectRepository.existsByNameAndCurrentObjectDifferent(subjectDTO.getName(), subjectRepository.findOne(subjectId))) {
                throw new AlreadyExistsException(model);
            }
            Subject subject = subjectRepository.findOne(subjectId);
            subject.setName(subjectDTO.getName());
            subjectRepository.save(subject);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void deleteSubject(String id) throws EmptyException {
        Long subjectId = Long.parseLong(HashUtils.decodeBase64(id));
        if (!subjectRepository.existsById(subjectId)) {
            throw new EmptyException(model);
        }
        for (Book book : bookRepository.findAllBySubject(subjectRepository.findOne(subjectId))) {
            book.setSubject(subjectRepository.findOne((Long.valueOf(1))));
            bookRepository.save(book);
        }
        subjectRepository.deleteById(subjectId);
    }

    public void deleteSubjectAndBooks(AuthorDTO authorDTO) throws EmptyException {
        Long authorId = Long.parseLong(HashUtils.decodeBase64(authorDTO.getId()));
        if (!subjectRepository.existsById(authorId)) {
            throw new EmptyException("Autor não encontrado");
        }
        bookRepository.deleteAll(bookRepository.findAllBySubject(subjectRepository.findOne(authorId)));
        subjectRepository.deleteById(authorId);
    }

    public List<SubjectDTO> list() throws ListEmptyException {
        List<Subject> subjects = subjectRepository.findAll();
        if (subjects.isEmpty()) {
            throw new ListEmptyException(model);
        }
        return this.generateListOfDTO(subjects);
    }

    public List<SubjectDTO> generateListOfDTO(List<Subject> subjects) {
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDTO subjectDTO = subject.parseToDTO();
            subjectDTOS.add(subjectDTO);
        }
        return subjectDTOS;
    }

    public SubjectDTO showAuthor(String id) throws EmptyException {
        Long subjectId = Long.parseLong(HashUtils.decodeBase64(id));
        if (!subjectRepository.existsById(subjectId)) {
            throw new EmptyException("Autor não encontrado");
        }

        return subjectRepository.findOne(subjectId).parseToDTO();
    }
    
}
