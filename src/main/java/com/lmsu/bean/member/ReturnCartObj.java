package com.lmsu.bean.member;

import com.lmsu.orderdata.orderitems.OrderItemDTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReturnCartObj implements Serializable {
    private Map<Integer, OrderItemDTO> returnItems;

    public Map<Integer, OrderItemDTO> getReturnItems() {
        return this.returnItems;
    }

    ;

    public void addBookToReturnCart(OrderItemDTO orderItem) {
        // 1. Check if cart existed
        this.returnItems = (this.returnItems == null) ? new HashMap<Integer, OrderItemDTO>() : this.returnItems;
        // 2. Update cart
        this.returnItems.put(orderItem.getId(), orderItem);
    }

    public void removeBookFromReturnCart(int orderItemsID) {
        // 1. Check if cart existed
        this.returnItems = (this.returnItems == null) ? new HashMap<Integer, OrderItemDTO>() : this.returnItems;
        // 2. Check if item existed
        if (this.returnItems.containsKey(orderItemsID)) {
            this.returnItems.remove(orderItemsID);
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
