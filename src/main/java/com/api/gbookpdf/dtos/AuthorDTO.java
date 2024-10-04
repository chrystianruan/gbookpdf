package com.api.gbookpdf.dtos;

import com.api.gbookpdf.entities.Author;
import com.api.gbookpdf.interfaces.IparseToObject;
import com.api.gbookpdf.utils.HashUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
public class AuthorDTO implements IparseToObject {
    private String id;
    private String name;

    @Override
    public Author parseToObject() {
        Author author = new Author();
        author.setId(Long.parseLong(HashUtils.decodeBase64(this.id)));
        author.setName(this.name);
        return author;
    }
}
