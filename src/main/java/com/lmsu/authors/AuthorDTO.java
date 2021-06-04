package com.lmsu.authors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class AuthorDTO implements Serializable {
    private String authorID;
    private String authorName;
    private String authorBio;
}
