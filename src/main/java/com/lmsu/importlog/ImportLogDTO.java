package com.lmsu.importlog;

import com.lmsu.books.BookDTO;
import com.lmsu.users.UserDTO;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class ImportLogDTO implements Serializable {
    private int logID;
    private BookDTO book;
    private UserDTO manager;
    private Date dateTaken;
    private String supplier;
    private int quantity;

    public ImportLogDTO() {
    }

    public ImportLogDTO(int logID, BookDTO book, UserDTO manager, Date dateTaken, String supplier, int quantity) {
        this.logID = logID;
        this.book = book;
        this.manager = manager;
        this.dateTaken = dateTaken;
        this.supplier = supplier;
        this.quantity = quantity;
    }
}
