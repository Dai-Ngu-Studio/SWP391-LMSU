package com.lmsu.bean.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartObj implements Serializable {
    private Map<String, BookObj> items;

    public Map<String, BookObj> getItems() {
        return this.items;
    }

    public int getCartQuantity() {
        return this.items.size();
    }

    public void addBookToCart(BookObj bookObj) {
        // 1. Check if cart existed
        this.items = (this.items == null) ? new HashMap<String, BookObj>() : this.items;
        // 2. Update cart
        this.items.put(bookObj.getId(), bookObj);
    }

    public void removeBookFromCart(String bookID) {
        // 1. Check if cart existed
        this.items = (this.items == null) ? new HashMap<String, BookObj>() : this.items;
        // 2. Check if item existed
        if (this.items.containsKey(bookID)) {
            this.items.remove(bookID);
            // 3. Check if cart empty
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

    public boolean isExistedInCart(String bookID) {
        // 1. Check if cart existed
        if (this.items == null) return false;
        // 2. Check if item existed
        if (this.items.containsKey(bookID)) {
            return true;
        }
        return false;
    }
}
