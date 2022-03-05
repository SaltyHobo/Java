/*
 *My submission for part 09_10.OnlineStore (Online Shop) of University of Helsinki’s MOOC on Java programming.
 *
 * @author SaltyHobo
 */

import java.util.Map;
import java.util.HashMap;
 
public class ShoppingCart {
    private Map<String, Item> product;
    
    public ShoppingCart() {
        this.product = new HashMap<>();
    }
    
    public void add(String product, int price) {
        if (this.product.keySet().contains(product)) {
            this.product.get(product).increaseQuantity();
        }
        if (this.product.get(product) == null) {
            Item item = new Item(product, 1, price);
            this.product.put(product, item);
        }
        
    }
    
    public int price() {
        int price = 0;
        for (Item i: this.product.values()) {
            price += i.price();
        }
        return price;
    }
    
    public void print() {
        for (String p: this.product.keySet()) {
            //System.out.println(p+": "+this.product.get(p).price());
            System.out.println(this.product.get(p).toString());
        }
    }
}
