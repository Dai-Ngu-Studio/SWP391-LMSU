package com.lmsu.subjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor

public @Data
class SubjectDTO implements Serializable {
    private String id;
    private String name;
    private int semester_no;
    private boolean deleteStatus;

    public SubjectDTO(String subjectID, String subjectName) {
        this.id = subjectID;
        this.name = subjectName;
    }
}

