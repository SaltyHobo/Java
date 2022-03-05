/*
 *My submission for part 09_10.OnlineStore (Online Shop) of University of Helsinki’s MOOC on Java programming.
 *
 * @author SaltyHobo
 */

import java.util.Scanner;

public class Store {
    private Warehouse warehouse;
    private Scanner scanner;
    
    public Store(Warehouse warehouse, Scanner scanner) {
        this.warehouse = warehouse;
        this.scanner = scanner;
    }
    
    public void shop(String customer) {
        ShoppingCart cart = new ShoppingCart();
        System.out.println("Welcome to the store "+customer);
        System.out.println("our selection:");
        
        for (String product: this.warehouse.products()) {
            System.out.println(product);
        }
        
        while (true) {
            //println instead of print
            System.out.println("What to put in the cart (press enter to go to the register):");
            String product = scanner.nextLine();
            if (product.isEmpty()) {
                break;
            }
            if (warehouse.stock(product) > 0) {
                cart.add(product, warehouse.price(product));
                warehouse.take(product);
            }
        }
        
        System.out.println("your shoppingcart contents:");
        cart.print();
        System.out.println("total: "+cart.price());
    }
}
