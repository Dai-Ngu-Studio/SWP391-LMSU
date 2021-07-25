package com.lmsu.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class UpdateUserErrorDTO implements Serializable {
    private String nameError;
    private String phoneNumberError;
    private String roleIDError;
    private String semesterError;
    private boolean activeStatusError;
}
