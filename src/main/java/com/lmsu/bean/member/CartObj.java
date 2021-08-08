package com.lmsu.bean.member;

import com.lmsu.bean.book.BookObj;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CartObj implements Serializable {
    private Map<String, BookObj> items;

    public Map<String, BookObj> getItems() {
        return this.items;
    }

    public int getCartQuantity() {
        if (this.items != null) {
            int cartQuantity = this.items.size();
            for (String bookID : this.items.keySet()) {
                BookObj book = this.items.get(bookID);
                if (book.getQuantity() == 0) {
                    cartQuantity--;
                }
            }
            return cartQuantity;
        }
        return 0;
    }

    // theoretically could be merged with getCartQuantity() but will complicate current JSP so don't merge
    public int getCartReserved() {
        if (this.items != null) {
            int cartReserved = 0;
            for (String bookID : this.items.keySet()) {
                BookObj book = this.items.get(bookID);
                if (book.getQuantity() == 0) {
                    cartReserved++;
                }
            }
            return cartReserved;
        }
        return 0;
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
        if (this.items != null) {
            if (this.items.containsKey(bookID)) {
                return true;
            }
        }
        return false;
    }
}
