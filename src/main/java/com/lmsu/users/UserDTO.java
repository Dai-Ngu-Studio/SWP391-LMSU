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
    private String email;
    private String phoneNumber;
    private String semester;
    private String profilePicturePath;
    private boolean activeStatus;
    private boolean isNotifyArrival;
    private boolean isNotifyPopular;
    private boolean isDelete;

    public UserDTO(String id, String name, String roleID, String password, String email,
                   String phoneNumber, String semester, String profilePicturePath) {
        this.id = id;
        this.name = name;
        this.roleID = roleID;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
        this.profilePicturePath = profilePicturePath;
    }

    public UserDTO(String name, String email){
        this.name = name;
        this.email = email;
    }
}
