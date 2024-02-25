package com.qino.model.dto;

import com.qino.model.entity.cast.ComposerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComposerDTO extends BaseDTO{
    private String firstName;
    private String secondName;
    private String fullName;
    private short age;

    public ComposerDTO(ComposerEntity composerEntity) {
        this.firstName = composerEntity.getFirstName();
        this.secondName = composerEntity.getSecondName();
        this.fullName = composerEntity.getFullName();
        this.age = composerEntity.getAge();
    }
}
