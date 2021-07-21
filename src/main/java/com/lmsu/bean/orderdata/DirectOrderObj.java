package com.lmsu.bean.orderdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class DirectOrderObj implements Serializable {
    private int orderID;
    private String librarianID;
    private String librarianName;
    private Timestamp scheduledTime;
    private boolean isReturnOrder;
}
