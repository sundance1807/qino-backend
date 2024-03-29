package com.qino.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qino.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDTO(UserEntity userEntity) {
        this.username = userEntity.getUsername();
    }
}

