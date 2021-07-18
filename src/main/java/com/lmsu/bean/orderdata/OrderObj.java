package com.lmsu.bean.orderdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class OrderObj implements Serializable {
    private int id;
    private String memberID;
    private String memberName;
    private Date orderDate;
    private boolean lendMethod;
    private int activeStatus;
}
