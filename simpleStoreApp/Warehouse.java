/*
 *My submission for part 09_10.OnlineStore (Online Shop) of University of Helsinki’s MOOC on Java programming.
 *
 * @author SaltyHobo
 */
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
 
public class Warehouse {
    private Map<String, Integer> product;
    private Map<String, Integer> stock;
    
    public Warehouse() {
        this.product = new HashMap<>();
        this.stock = new HashMap<>();
    }
    
    public void addProduct(String product, int price, int stock) {
        this.product.put(product, price);
        this.stock.put(product, stock);
    }
    
    public int price(String product) {
        if (this.product.get(product) == null) {
            return -99;
        }
        return this.product.get(product);
    }
    
    public int stock(String product) {
        if (this.stock.get(product) == null) {
            return 0;
        }
        return this.stock.get(product);
    }
    
    public boolean take(String product) {
        if (this.stock.get(product) == null) {
            return false;
        }
        if (this.stock.get(product) < 1) {
            return false;
        }
        this.stock.put(product, this.stock.get(product)-1);
        return true;
    }
    
    public Set<String> products() {
        Set<String> products = new HashSet<>();
        for(String p: this.stock.keySet()) {
            products.add(p);
        }
        return products;
    }
    
    @Override
    public String toString() {
        return this.product+"";
    }
    
    
}
