package com.api.gbookpdf.entities;

import com.api.gbookpdf.dtos.SubjectDTO;
import com.api.gbookpdf.utils.HashUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter @Setter
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public SubjectDTO parseToDTO() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(HashUtils.encodeBase64(this.getId().toString()));
        subjectDTO.setName(this.getName());

        return subjectDTO;
    }
}
