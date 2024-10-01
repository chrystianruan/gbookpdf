package com.api.gbookpdf.dtos;

import com.api.gbookpdf.entities.Author;
import com.api.gbookpdf.utils.HashUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
public class AuthorDTO {
    private String id;
    private String name;

    public Author toAuthor() {
        Author author = new Author();
        author.setId(Long.parseLong(HashUtils.decodeBase64(this.id)));
        author.setName(this.name);
        return author;
    }
}
