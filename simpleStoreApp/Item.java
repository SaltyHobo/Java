/*
 *My submission for part 09_10.OnlineStore (Online Shop) of University of Helsinki’s MOOC on Java programming.
 *
 * @author SaltyHobo
 */

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
 
public class Item {
    private Map<String, Integer> itemQty = new HashMap<>();
    private Map<String, Integer> itemPrice = new HashMap<>();
    
    public Item(String product, int qty, int unitPrice) {
        this.itemQty.put(product, qty);
        this.itemPrice.put(product,unitPrice);
    }
    
    public int price() {
        int price = 0;
        for (Integer qty: itemQty.values()) {
            price = qty;
        }
        for (Integer prc: itemPrice.values()) {
            price *= prc;
        }
        return price;
    }
    
    public void increaseQuantity() {
        int newItemQty = 1;
        for (Integer q: this.itemQty.values()) {
            newItemQty += q;
        }
        String product = "";
        for (String p: this.itemQty.keySet()) {
            product = p;
        }
        this.itemQty.put(product, newItemQty);
    }
    
    @Override
    public String toString() {
        int qty = 0;
        for (Integer q: this.itemQty.values()) {
            qty += q;
        }
        String product = "";
        for (String p: this.itemQty.keySet()) {
            product = p;
        }
        return product+": "+qty;
    }
}
