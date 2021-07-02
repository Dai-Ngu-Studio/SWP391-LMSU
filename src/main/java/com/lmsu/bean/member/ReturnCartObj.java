package com.lmsu.bean.member;

import com.lmsu.bean.orderdata.OrderItemObj;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReturnCartObj implements Serializable {
    private Map<Integer, OrderItemObj> returnItems;

    public Map<Integer, OrderItemObj> getReturnItems() {
        return this.returnItems;
    };

    public void addBookToReturnCart(OrderItemObj orderItemObj) {
        // 1. Check if cart existed
        this.returnItems = (this.returnItems == null) ? new HashMap<Integer, OrderItemObj>() : this.returnItems;
        // 2. Update cart
        this.returnItems.put(orderItemObj.getId(), orderItemObj);
    }

    public void removeBookFromReturnCart(int orderItemPk) {
        // 1. Check if cart existed
        this.returnItems = (this.returnItems == null) ? new HashMap<Integer, OrderItemObj>() : this.returnItems;
        // 2. Check if item existed
        if (this.returnItems.containsKey(orderItemPk)) {
            this.returnItems.remove(orderItemPk);
            // 3. Check if cart empty
            if (this.returnItems.isEmpty()) {
                this.returnItems = null;
            }
        }
    }

    public boolean isExistedInCart(int orderItemsID) {
        if (this.returnItems != null) {
            if (this.returnItems.containsKey(orderItemsID)) {
                return true;
            }
        }
        return false;
    }
}
