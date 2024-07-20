import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

class Product {
    private final String productId;
    private String productName;
    private int stock;
    private final int price;

    Product(String productId, String productName, int stock, int price) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
    }

    String getProductId() {
        return productId;

    }

    String productName() {
        return productName;

    }

    int Stock() {
        return stock;

    }

    void setStock(int val) {
        stock = val;

    }

    int Price() {
        return price;

    }

}

class Store {
    private List<Product> productList;

    public Store() {
        productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> searchProduct(String productName) {
        List<Product> list = new ArrayList<>();
        for (Product product : productList) {
            if (product.productName().equals(productName)) {
                list.add(product);
            }
        }
        return list;

    }

    public void searchProductDetails(String productName) {
        for (Product product : searchProduct(productName)) {
            System.out.println("Product Details " + "product Id: " + product.getProductId() + ", product Name: "
                    + product.productName()
                    + ", price: " + product.Price() + ", Stock " + product.Stock());
        }
    }

    public void displaySearchedProduct(List<Product> list) {
        if (list.size() > 0) {
            for (Product product : list) {
                System.out.println("Product Details " + "product Id: " + product.getProductId() + ", product Name: "
                        + product.productName()
                        + ", price: " + product.Price() + ", Stock " + product.Stock());
            }
        } else {
            throw new IllegalStateException("Product is not available");
        }
    }

    public boolean isStockAvailable(String productName, int quantity) {

        for (Product product : productList) {
            if (product.productName().equals(productName) && product.Stock() >= quantity) {
                return true;

            }

        }
        return false;

    }

    public void buyProduct(Customer customer, String productName, int quantity) {
        if (isStockAvailable(productName, quantity)) {
            for (Product product : productList) {
                System.out.println(product.productName());
                if (product.productName().equals(productName) && customer.getCredits() >= product.Price() * quantity) {
                    product.setStock(product.Stock() - quantity);
                    double bill = quantity * product.Price();
                    customer.setCredits(customer.getCredits() - bill);
                    LocalDate currentDate = LocalDate.now();
                    String successOrder = "order Id : " + UUID.randomUUID().toString() + " Date : " + currentDate
                            + " Purchased " + quantity
                            + " units of " + productName + " and your bill is: "
                            + bill;
                    customer.addOrder(successOrder);
                    System.out.println(successOrder);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Product is out of stock");
        }
    }

    void displayProducts() {
        for (Product product : productList) {
            System.out.println("Product Details " + "product Id: " + product.getProductId() + ", product Name: "
                    + product.productName()
                    + ", price: " + product.Price() + ", Stock " + product.Stock());

        }

    }

}

class Customer {
    private final String id;
    private String name;
    private double credits;
    private List<String> orders;

    Customer(String id, String name, double credits) {

        this.id = id;
        this.name = name;
        this.credits = credits;
        this.orders = new ArrayList<>();

    }

    String getCustomerId() {
        return id;
    }

    String getCustomerName() {
        return name;
    }

    double getCredits() {
        return credits;
    }

    void setCredits(double creditValue) {
        credits = creditValue;
    }

    public List<String> getOrderDetails() {
        return orders;
    }

    public void addOrder(String order) {

        orders.add(order);
    }

    public void myOrders() {
        for (String str : orders) {
            System.out.println(str);

        }
    }

}

public class Main {
    public static void main(String[] args) {
        Store store = new Store();

        // Add products to the store
        Product product1 = new Product(UUID.randomUUID().toString(), "mobile", 100, 1000);
        Product product2 = new Product(UUID.randomUUID().toString(), "shoes", 100, 100);
        store.addProduct(product1);
        store.addProduct(product2);

        List<Customer> customers = new ArrayList<>();
        Customer currentCustomer = null;

        Scanner sc = new Scanner(System.in);
        int userChoice;

        do {
            System.out.println("------------------------------------");
            System.out.println("1. See available products");
            System.out.println("2. Buy a product");
            System.out.println("3. My Prev Order");
            System.out.println("4. Search Particular Product / stock");
            System.out.println("5. Create New Customer");
            System.out.println("6. Check Account Details");
            System.out.println("Enter 0 to exit");
            System.out.println("Enter your choice: ");

            userChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (userChoice) {
                case 1:
                    store.displayProducts();
                    break;
                case 2:
                    if (currentCustomer == null) {
                        System.out.println("Unauthorized User , Please SignUp");
                        break;
                    }
                    store.displayProducts();
                    System.out.println("Enter Product Name:");
                    String productName = sc.nextLine();
                    System.out.println("Enter quantity: ex.1 ");
                    int quantity = sc.nextInt();
                    if (store.isStockAvailable(productName.toLowerCase(), quantity)) {
                        store.buyProduct(currentCustomer, productName.toLowerCase(), quantity);
                    } else {
                        System.out.println("Product is out of stock");
                    }
                    break;
                case 3:
                    if (currentCustomer == null) {
                        System.out.println("Unauthorized User , Please SignUp");
                        break;
                    }
                    System.out.println("Your Previous Orders are: ");
                    currentCustomer.myOrders();
                    break;
                case 4:
                    System.out.println("Check stock of a particular product");
                    System.out.println("Enter Product Name:");
                    String str = sc.nextLine();
                    store.searchProductDetails(str.toLowerCase());
                    break;
                case 5:
                    System.out.println("Enter customer name:");
                    String customerName = sc.nextLine();
                    System.out.println("Enter initial credits:");
                    double credits = sc.nextDouble();
                    Customer newCustomer = new Customer(UUID.randomUUID().toString(), customerName, credits);
                    customers.add(newCustomer);
                    currentCustomer = newCustomer;
                    System.out.println("Created and selected customer: " + currentCustomer.getCustomerName());
                    break;
                case 6:
                    if (currentCustomer == null) {
                        System.out.println("Unauthorized User , Please SignUp");
                        break;
                    }
                    System.out.println("Name: " + currentCustomer.getCustomerName());
                    System.out.println("Credits: " + currentCustomer.getCredits());
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (userChoice != 0);
        sc.close();
    }
}