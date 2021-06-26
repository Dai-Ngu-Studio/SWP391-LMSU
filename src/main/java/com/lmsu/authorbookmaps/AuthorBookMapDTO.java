package com.lmsu.authorbookmaps;

import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor

public @Data
class AuthorBookMapDTO implements Serializable {
    private int id;
    private AuthorDTO authorDTO;
    private BookDTO bookDTO;

    public AuthorBookMapDTO(int id, AuthorDTO author, BookDTO book) {
        this.id = id;
        this.authorDTO = author;
        this.bookDTO = book;
    }
}
