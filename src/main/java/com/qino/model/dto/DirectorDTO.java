package com.qino.model.dto;

import com.qino.model.entity.UserEntity;
import com.qino.model.entity.cast.DirectorEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DirectorDTO extends BaseDTO{
    private String firstName;
    private String secondName;
    private String fullName;
    private short age;

    public DirectorDTO(DirectorEntity directorEntity) {
        this.firstName = directorEntity.getFirstName();
        this.secondName = directorEntity.getSecondName();
        this.fullName = directorEntity.getFullName();
        this.age = directorEntity.getAge();
    }
}
