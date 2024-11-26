import java.util.*;

//Cake class to store cake details, including price and quantity
class Cake {
 String name;
 double priceSmall;
 double priceMedium;
 double priceLarge;
 int quantitySmall;
 int quantityMedium;
 int quantityLarge;

 public Cake(String name, double priceSmall, double priceMedium, double priceLarge, int quantitySmall, int quantityMedium, int quantityLarge) {
     this.name = name;
     this.priceSmall = priceSmall;
     this.priceMedium = priceMedium;
     this.priceLarge = priceLarge;
     this.quantitySmall = quantitySmall;
     this.quantityMedium = quantityMedium;
     this.quantityLarge = quantityLarge;
 }

 public double getPriceBySize(String size) {
     switch (size.toLowerCase()) {
         case "small":
             return priceSmall;
         case "medium":
             return priceMedium;
         case "large":
             return priceLarge;
         default:
             return 0.0;
     }
 }

 public boolean decrementQuantity(String size) {
     switch (size.toLowerCase()) {
         case "small":
             if (quantitySmall > 0) {
                 quantitySmall--;
                 return true;
             }
             break;
         case "medium":
             if (quantityMedium > 0) {
                 quantityMedium--;
                 return true;
             }
             break;
         case "large":
             if (quantityLarge > 0) {
                 quantityLarge--;
                 return true;
             }
             break;
     }
     return false; // No stock available
 }
}

//CakeBSTNode class for Binary Search Tree (BST) nodes
class CakeBSTNode {
 Cake cake; // The cake object
 CakeBSTNode left, right; // Left and right children

 public CakeBSTNode(Cake cake) {
     this.cake = cake;
     this.left = this.right = null;
 }
}

//Order History (Linked List)
class OrderHistoryNode {
 String orderDetails;
 OrderHistoryNode next;

 public OrderHistoryNode(String orderDetails) {
     this.orderDetails = orderDetails;
     this.next = null;
 }
}

//Custom Queue for Orders
class OrderQueueNode {
 String orderDetails;
 OrderQueueNode next;

 public OrderQueueNode(String orderDetails) {
     this.orderDetails = orderDetails;
     this.next = null;
 }
}

//Hash Table Node for Customer Lookup
class CustomerHashNode {
 String key;
 Customer customer;
 CustomerHashNode next;

 public CustomerHashNode(String key, Customer customer) {
     this.key = key;
     this.customer = customer;
     this.next = null;
 }
}

//Customer class to store customer info
class Customer {
 String name;
 String email;
 String phone;
 String address;
 String deliveryOption;

 public Customer(String name, String email, String phone, String address, String deliveryOption) {
     this.name = name;
     this.email = email;
     this.phone = phone;
     this.address = address;
     this.deliveryOption = deliveryOption;
 }
}

//Main Cake Shop Management System class
public class CakeShopManagement {
 // Linked List for Order History
 private OrderHistoryNode orderHistoryHead = null;

 // Custom Queue for Incoming Orders
 private OrderQueueNode orderQueueFront = null;
 private OrderQueueNode orderQueueRear = null;

 // Binary Search Tree (BST) for Cake Catalog
 private CakeBSTNode cakeCatalogRoot = null;

 // Hash Table for Customer Lookup
 private CustomerHashNode[] customerHashTable = new CustomerHashNode[100]; // Simple hash table with 100 slots

 // Function to add an order to the Order Queue
 public void addOrderToQueue(String orderDetails) {
     OrderQueueNode newOrder = new OrderQueueNode(orderDetails);
     if (orderQueueRear == null) {
         orderQueueFront = orderQueueRear = newOrder;
     } else {
         orderQueueRear.next = newOrder;
         orderQueueRear = newOrder;
     }
 }

 // Function to process the next order from the queue
 public String processNextOrder() {
     if (orderQueueFront == null) {
         return "No orders in the queue.";
     }
     String order = orderQueueFront.orderDetails;
     orderQueueFront = orderQueueFront.next;
     if (orderQueueFront == null) {
         orderQueueRear = null;
     }
     return "Processed order: " + order;
 }

 // Function to add a completed order to Order History (Linked List)
 public void addOrderToHistory(String orderDetails) {
     OrderHistoryNode newNode = new OrderHistoryNode(orderDetails);
     if (orderHistoryHead == null) {
         orderHistoryHead = newNode;
     } else {
         OrderHistoryNode temp = orderHistoryHead;
         while (temp.next != null) {
             temp = temp.next;
         }
         temp.next = newNode;
     }
 }

 // Function to display Order History
 public void displayOrderHistory() {
     OrderHistoryNode temp = orderHistoryHead;
     if (temp == null) {
         System.out.println("No order history available.");
     }
     while (temp != null) {
         System.out.println(temp.orderDetails);
         temp = temp.next;
     }
 }

 // Function to add a cake to the BST (Cake Catalog)
 public void addCakeToCatalog(Cake cake) {
     cakeCatalogRoot = insertCakeToBST(cakeCatalogRoot, cake);
 }

 // Helper function to insert a cake into BST
 private CakeBSTNode insertCakeToBST(CakeBSTNode root, Cake cake) {
     if (root == null) {
         return new CakeBSTNode(cake);
     }
     if (cake.priceMedium < root.cake.priceMedium) {  // Use medium price as default sorting criteria
         root.left = insertCakeToBST(root.left, cake);
     } else {
         root.right = insertCakeToBST(root.right, cake);
     }
     return root;
 }

 // Function to display the cakes in the catalog (In-order traversal)
 public void displayCakeCatalog(boolean isEmployee) {
     inOrderTraversal(cakeCatalogRoot, isEmployee);
 }

 // In-order traversal of BST to display cakes
 private void inOrderTraversal(CakeBSTNode root, boolean isEmployee) {
     if (root != null) {
         inOrderTraversal(root.left, isEmployee);
         if (isEmployee) {
             System.out.printf("%-20s %-15.2f %-15.2f %-15.2f %-10d %-10d %-10d\n", root.cake.name, root.cake.priceSmall, root.cake.priceMedium, root.cake.priceLarge, root.cake.quantitySmall, root.cake.quantityMedium, root.cake.quantityLarge);
         } else {
             System.out.printf("%-20s %-15.2f %-15.2f %-15.2f\n", root.cake.name, root.cake.priceSmall, root.cake.priceMedium, root.cake.priceLarge);
         }
         inOrderTraversal(root.right, isEmployee);
     }
 }

 // Function to add customer to Hash Table
//Function to add customer to Hash Table
public void addCustomer(String key, Customer customer) {
  int index = Math.abs(key.hashCode()) % customerHashTable.length;  // Use absolute value to avoid negative index
  CustomerHashNode newCustomerNode = new CustomerHashNode(key, customer);
  newCustomerNode.next = customerHashTable[index];
  customerHashTable[index] = newCustomerNode;
}


 // Function to get customer details by email (or unique key)
 public Customer getCustomer(String key) {
     int index = key.hashCode() % customerHashTable.length;
     CustomerHashNode current = customerHashTable[index];
     while (current != null) {
         if (current.key.equals(key)) {
             return current.customer;
         }
         current = current.next;
     }
     return null;
 }

 // Function to get the price of a cake based on name and size
 private double getCakePrice(String cakeName, String size) {
     return getCakePrice(cakeCatalogRoot, cakeName, size);
 }

 // Helper function to search for the cake in the BST and return the price
 private double getCakePrice(CakeBSTNode root, String cakeName, String size) {
     if (root == null) return 0.0; // Cake not found
     // If the cake is found, return the price based on the size
     if (root.cake.name.equalsIgnoreCase(cakeName)) {
         return root.cake.getPriceBySize(size);
     }
     if (cakeName.compareToIgnoreCase(root.cake.name) < 0) {
         return getCakePrice(root.left, cakeName, size);
     } else {
         return getCakePrice(root.right, cakeName, size);
     }
 }

 // Function to place an order
 public void placeOrder(String cakeName, String size, Customer customer) {
     double price = getCakePrice(cakeName, size);
     if (price > 0) {
         boolean orderPlaced = false;
         CakeBSTNode cakeNode = findCakeNode(cakeCatalogRoot, cakeName);
         if (cakeNode != null && cakeNode.cake.decrementQuantity(size)) {
             addOrderToQueue("Order placed for: " + cakeName + " (" + size + ") by " + customer.name + ", Delivery: " + customer.deliveryOption + " to " + customer.address);
             addOrderToHistory("Order placed for: " + cakeName + " (" + size + ") by " + customer.name);
             orderPlaced = true;
             // Print Bill
             printBill(cakeName, size, price);
         }
         if (!orderPlaced) {
             System.out.println("Sorry, the cake is out of stock in this size.");
         }
     } else {
         System.out.println("Cake not found.");
     }
 }

 // Function to print the bill for the customer
 private void printBill(String cakeName, String size, double price) {
     System.out.println("\n--- Bill ---");
     System.out.println("Cake Name: " + cakeName);
     System.out.println("Size: " + size);
     System.out.println("Total Price: $" + price);
     System.out.println("----------------");
 }

 // Helper function to find a cake by name in BST
 private CakeBSTNode findCakeNode(CakeBSTNode root, String cakeName) {
     if (root == null) return null;
     if (root.cake.name.equalsIgnoreCase(cakeName)) return root;
     if (cakeName.compareToIgnoreCase(root.cake.name) < 0) {
         return findCakeNode(root.left, cakeName);
     } else {
         return findCakeNode(root.right, cakeName);
     }
 }

 // Employee Menu
 public void showEmployeeMenu() {
     Scanner scanner = new Scanner(System.in);
     System.out.println("Enter password:");
	 String password=scanner.nextLine();
	 if(password.equals("Admin123")) {
    	 System.out.println("Access granted!");
    	 try {
     int choice;
     do {
    	 
         System.out.println("\nEmployee Menu");
         System.out.println("1. View Cake Catalog");
         System.out.println("2. Add Cake to Catalog");
         System.out.println("3. View Order History");
         System.out.println("4. Process Orders");
         System.out.println("5. Switch to Customer");
         System.out.println("6. Exit");
         System.out.print("Enter your choice: ");
         choice = scanner.nextInt();
         scanner.nextLine(); // Consume newline
         switch (choice) {
             case 1:
                 System.out.println("\n--- Cake Catalog ---");
                 displayCakeCatalog(true);
                 break;
             case 2:
                 // Add cake to catalog
                 System.out.print("Enter cake name: ");
                 String name = scanner.nextLine();
                 System.out.print("Enter small size price: ");
                 double priceSmall = scanner.nextDouble();
                 System.out.print("Enter medium size price: ");
                 double priceMedium = scanner.nextDouble();
                 System.out.print("Enter large size price: ");
                 double priceLarge = scanner.nextDouble();
                 System.out.print("Enter quantity for small size: ");
                 int quantitySmall = scanner.nextInt();
                 System.out.print("Enter quantity for medium size: ");
                 int quantityMedium = scanner.nextInt();
                 System.out.print("Enter quantity for large size: ");
                 int quantityLarge = scanner.nextInt();
                 scanner.nextLine(); // Consume newline
                 Cake newCake = new Cake(name, priceSmall, priceMedium, priceLarge, quantitySmall, quantityMedium, quantityLarge);
                 addCakeToCatalog(newCake);
                 break;
            	
             case 3:
                 System.out.println("\n--- Order History ---");
                 displayOrderHistory();
                 break;
             case 4:
                 System.out.println("\n--- Process Orders ---");
                 String processedOrder = processNextOrder();
                 System.out.println(processedOrder);
                 break;
             case 5:
                 System.out.println("Switching to Customer menu...");
                 showCustomerMenu();
                 break;
             case 6:
                 System.out.println("Thank you for using the Cake Shop Management System!");
                 break;
             default:
                 System.out.println("Invalid choice, please try again.");
         }
         
     } while (choice != 6);
    	 }catch(Exception e) {
    		 System.out.println("error, try again...");
    	 }
	 }
    	 
	 else {
    	 System.out.println("Access denied");
	 }
	 
 }

 // Customer Menu
 public void showCustomerMenu() {
     Scanner scanner = new Scanner(System.in);
     int choice;
     try {
     do {
         System.out.println("\nCustomer Menu");
         System.out.println("1. View Cake Catalog");
         System.out.println("2. Place Order");
         System.out.println("3. Switch to Employee");
         System.out.println("4. Exit");
         System.out.print("Enter your choice: ");
         choice = scanner.nextInt();
         scanner.nextLine(); // Consume newline
         switch (choice) {
             case 1:
                 System.out.println("\n--- Cake Catalog ---");
                 displayCakeCatalog(false);
                 break;
             case 2:
                 System.out.print("Enter your name: ");
                 String name = scanner.nextLine();
                 System.out.print("Enter your email: ");
                 String email = scanner.nextLine();
                 System.out.print("Enter your phone: ");
                 String phone = scanner.nextLine();
                 System.out.print("Enter your address: ");
                 String address = scanner.nextLine();
                 System.out.print("Enter delivery option (Standard/Express): ");
                 String deliveryOption = scanner.nextLine();
                 Customer customer = new Customer(name, email, phone, address, deliveryOption);
                 addCustomer(email, customer);
                 System.out.print("Enter cake name to order: ");
                 String cakeName = scanner.nextLine();
                 System.out.print("Enter cake size (Small/Medium/Large): ");
                 String size = scanner.nextLine();
                 placeOrder(cakeName, size, customer);
                 break;
            	 
             case 3:
            	 
                 System.out.println("Switching to Employee menu...");
                 showEmployeeMenu();
                 break;
             case 4:
                 System.out.println("Thank you for using the Cake Shop Management System!");
                 break;
             default:
                 System.out.println("Invalid choice, please try again.");
         }
     } while (choice != 4);
     }catch(Exception e) {
		 System.out.println("error, try again...");
	 }
 }

 // Main function to start the application
 public static void main(String[] args) {
     CakeShopManagement cakeShop = new CakeShopManagement();
     Scanner scanner = new Scanner(System.in);

     // Show user options: Employee or Customer
     System.out.println("Welcome to the Cake Shop Management System!");
     System.out.print("Are you an Employee or Customer? (Enter E for Employee, C for Customer): ");
     String userType = scanner.nextLine();

     if (userType.equalsIgnoreCase("E")) {
    		 cakeShop.showEmployeeMenu();
     } else if (userType.equalsIgnoreCase("C")) {
         cakeShop.showCustomerMenu();
     } else {
         System.out.println("Invalid input, please restart the program.");
     }
 }
}
/*Welcome to the Cake Shop Management System!
Are you an Employee or Customer? (Enter E for Employee, C for Customer): e
Enter password:
Admin123
Access granted!

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 2
Enter cake name: chocolate
Enter small size price: 200
Enter medium size price: 300
Enter large size price: 400
Enter quantity for small size: 12
Enter quantity for medium size: 6
Enter quantity for large size: 3

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 2
Enter cake name: red velvet
Enter small size price: 350
Enter medium size price: 450
Enter large size price: 550
Enter quantity for small size: 10
Enter quantity for medium size: 5
Enter quantity for large size: 3

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 2
Enter cake name: belgium chocolate
Enter small size price: 400
Enter medium size price: 500
Enter large size price: 600
Enter quantity for small size: 5
Enter quantity for medium size: 6
Enter quantity for large size: 7

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 1

--- Cake Catalog ---
chocolate            200.00          300.00          400.00          12         6          3         
red velvet           350.00          450.00          550.00          10         5          3         
belgium chocolate    400.00          500.00          600.00          5          6          7         

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 3

--- Order History ---
No order history available.

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 4

--- Process Orders ---
No orders in the queue.

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 5
Switching to Customer menu...

Customer Menu
1. View Cake Catalog
2. Place Order
3. Switch to Employee
4. Exit
Enter your choice: 1

--- Cake Catalog ---
chocolate            200.00          300.00          400.00         
red velvet           350.00          450.00          550.00         
belgium chocolate    400.00          500.00          600.00         

Customer Menu
1. View Cake Catalog
2. Place Order
3. Switch to Employee
4. Exit
Enter your choice: 2
Enter your name: Sunil Sharma
Enter your email: sunil@gmail.com
Enter your phone: 7972629866
Enter your address: aundh, pune
Enter delivery option (Standard/Express): Standard
Enter cake name to order: red velvet
Enter cake size (Small/Medium/Large): small

--- Bill ---
Cake Name: red velvet
Size: small
Total Price: $350.0
----------------

Customer Menu
1. View Cake Catalog
2. Place Order
3. Switch to Employee
4. Exit
Enter your choice: 2
Enter your name: jdf
Enter your email: fdjkg@jdfkgf
Enter your phone: 84593845389
Enter your address: jgdhgd
Enter delivery option (Standard/Express): Express
Enter cake name to order: chocolate
Enter cake size (Small/Medium/Large): Medium

--- Bill ---
Cake Name: chocolate
Size: Medium
Total Price: $300.0
----------------

Customer Menu
1. View Cake Catalog
2. Place Order
3. Switch to Employee
4. Exit
Enter your choice: 3
Switching to Employee menu...
Enter password:
Admin123
Access granted!

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 3

--- Order History ---
Order placed for: red velvet (small) by Sunil Sharma
Order placed for: chocolate (Medium) by jdf

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 4

--- Process Orders ---
Processed order: Order placed for: red velvet (small) by Sunil Sharma, Delivery: Standard to aundh, pune

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 4

--- Process Orders ---
Processed order: Order placed for: chocolate (Medium) by jdf, Delivery: Express to jgdhgd

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 3

--- Order History ---
Order placed for: red velvet (small) by Sunil Sharma
Order placed for: chocolate (Medium) by jdf

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 4

--- Process Orders ---
No orders in the queue.

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 6
Thank you for using the Cake Shop Management System!

Customer Menu
1. View Cake Catalog
2. Place Order
3. Switch to Employee
4. Exit
Enter your choice: 4
Thank you for using the Cake Shop Management System!

Employee Menu
1. View Cake Catalog
2. Add Cake to Catalog
3. View Order History
4. Process Orders
5. Switch to Customer
6. Exit
Enter your choice: 6
Thank you for using the Cake Shop Management System!

*/
