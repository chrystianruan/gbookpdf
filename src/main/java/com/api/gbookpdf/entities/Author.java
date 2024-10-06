package com.api.gbookpdf.entities;

import com.api.gbookpdf.dtos.AuthorDTO;
import com.api.gbookpdf.utils.HashUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter @Setter
@SQLDelete(sql = "UPDATE authors SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is null")
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "book")
    private List<Book> books;



    public AuthorDTO parseToDTO() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(HashUtils.encodeBase64(Long.toString(this.id)));
        authorDTO.setName(this.name);

        return authorDTO;
    }
}
