package com.lmsu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class OrderItemsObj implements Serializable {
    private int id;
    private int orderID;
    private String memberID;
    private String bookID;
    private String title;
    private int lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;

    public OrderItemsObj(){ }

    public OrderItemsObj(int id, int orderID, String  memberID, String bookID, String title, int lendStatus,
                         Date returnDeadline, Date lendDate, Date returnDate){
        this.id = id;
        this.orderID = orderID;
        this.memberID = memberID;
        this.bookID = bookID;
        this.title = title;
        this.lendStatus = lendStatus;
        this.returnDeadline = returnDeadline;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public int getLendStatus() {
        return lendStatus;
    }

    public Date getReturnDeadline() {
        return returnDeadline;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
