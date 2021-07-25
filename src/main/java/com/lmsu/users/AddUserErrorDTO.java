package com.lmsu.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class AddUserErrorDTO implements Serializable {
    private String idError;
    private String nameError;
    private String roleIDError;
    private String passwordError;
    private String emailError;
    private String phoneNumberError;
    private String semesterError;
}
