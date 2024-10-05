package com.api.gbookpdf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private AuthorDTO author;
    private SubjectDTO subject;
    private String description;

}
