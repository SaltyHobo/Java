/*
 *My submission for part 09_10.OnlineStore (Online Shop) of University of Helsinki’s MOOC on Java programming.
 *
 * @author SaltyHobo
 */

import java.util.Scanner;
 
public class Main {
 
    public static void main(String[] args) {
        // Test your app here
        Scanner scanner = new Scanner(System.in);
        
        Warehouse warehouse = new Warehouse();
        
        warehouse.addProduct("milk", 3, 10);
        
        System.out.println("prices:");
        System.out.println("milk: "+warehouse.price("milk"));
        
        System.out.println("-----");
        
        warehouse.addProduct("coffee", 5, 1);
        
        System.out.println("Stock:");
        System.out.println("coffee: "+warehouse.stock("coffee"));
        System.out.println("sugar: "+warehouse.stock("sugar"));
        
        System.out.println("taking cofee "+warehouse.take("coffee"));
        System.out.println("taking cofee "+warehouse.take("coffee"));
        System.out.println("taking sugar "+warehouse.take("sugar"));
        
        System.out.println("Stock:");
        System.out.println("coffee: "+warehouse.stock("coffee"));
        System.out.println("sugar: "+warehouse.stock("sugar"));
        
        System.out.println("-----");
        
        System.out.println("Products:");
        for (String p: warehouse.products()) {
            System.out.println(p);
        }
        
        System.out.println("-----");
        
        Item item = new Item("milk", 4, 2);
        System.out.println("4 milks costs: "+item.price());
        System.out.println(item);
        item.increaseQuantity();
        System.out.println(item);
        
        System.out.println("-----");
        
        ShoppingCart cart = new ShoppingCart();
        cart.add("milk", 3);
        cart.add("buttermilk", 2);
        cart.add("cheese", 5);
        System.out.println("cart price: "+cart.price());
        cart.add("computer", 899);
        System.out.println("cart price: "+cart.price());
        cart.print();
        
        System.out.println("-----");
        
        Store store = new Store(warehouse, scanner);
        store.shop("John");
    }
}
