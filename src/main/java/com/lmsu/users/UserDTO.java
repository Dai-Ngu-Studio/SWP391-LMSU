package com.lmsu.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class UserDTO implements Serializable {
    private String id;
    private String name;
    private String roleID;
    private String password;
    private String passwordGoogle;
    private String email;
    private String phoneNumber;
    private String semester; // thiếu trong DB
    private String profilePicturePath;
}
