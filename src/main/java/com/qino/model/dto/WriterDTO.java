package com.qino.model.dto;

import com.qino.model.entity.cast.DirectorEntity;
import com.qino.model.entity.cast.WriterEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WriterDTO extends BaseDTO{
    private String firstName;
    private String secondName;
    private String fullName;
    private short age;

    public WriterDTO(WriterEntity writerEntity) {
        this.firstName = writerEntity.getFirstName();
        this.secondName = writerEntity.getSecondName();
        this.fullName = writerEntity.getFullName();
        this.age = writerEntity.getAge();
    }
}
