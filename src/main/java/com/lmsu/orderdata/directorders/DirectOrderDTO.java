package com.lmsu.orderdata.directorders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor

public @Data
class DirectOrderDTO implements Serializable {
    private int orderID;
    private String librarianID;
    private Timestamp scheduledTime;
    private boolean isReturnOrder;
}
