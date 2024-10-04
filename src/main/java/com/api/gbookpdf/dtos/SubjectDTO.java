package com.api.gbookpdf.dtos;

import com.api.gbookpdf.entities.Subject;
import com.api.gbookpdf.interfaces.IparseToObject;
import com.api.gbookpdf.utils.HashUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO implements IparseToObject {
    private String id;
    private String name;

    @Override
    public Subject parseToObject() {
        Subject subject = new Subject();
        subject.setId(Long.parseLong(HashUtils.decodeBase64(this.getId())));
        subject.setName(this.getName());

        return subject;
    }


}
