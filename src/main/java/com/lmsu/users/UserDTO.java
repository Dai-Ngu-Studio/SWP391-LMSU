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
    private String semester;
    private String profilePicturePath;
    private boolean activeStatus;

    public UserDTO(String id, String name, String roleID, String password, String passwordGoogle,
                   String email, String phoneNumber, String semester, String profilePicturePath) {
        this.id = id;
        this.name = name;
        this.roleID = roleID;
        this.password = password;
        this.passwordGoogle = passwordGoogle;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
        this.profilePicturePath = profilePicturePath;
    }
}
