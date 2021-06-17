package com.lmsu.importlog;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

public @Data
class ImportLogDTO implements Serializable {
    private int logID;
    private String bookID;
    private String managerID;
    private Date dateTaken;
    private String supplier;
    private int quantity;

    public ImportLogDTO(){
    }

    public ImportLogDTO(int logID, String bookID, String managerID, Date dateTaken, String supplier, int quantity){
        this.logID = logID;
        this.bookID = bookID;
        this.managerID = managerID;
        this.dateTaken = dateTaken;
        this.supplier = supplier;
        this.quantity = quantity;
    }

}
