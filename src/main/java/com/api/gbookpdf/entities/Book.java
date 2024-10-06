package com.api.gbookpdf.entities;
import com.api.gbookpdf.dtos.BookDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne
    @JoinColumn(name = "subject'_id")
    private Subject subject;
    @OneToMany(mappedBy = "book")
    private List<User> users;

    public BookDTO toDTO() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(this.author.parseToDTO());
        bookDTO.setSubject(this.subject.parseToDTO());
        bookDTO.setTitle(this.title);
        bookDTO.setDescription(this.description);
        return bookDTO;
    }

}
