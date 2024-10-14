package Nguoi;


import java.util.ArrayList;
import java.util.List;

public class User extends Nguoi implements Purchasable {
    private String username;
    private String password;
    private List<String> purchaseHistory;
    private static int totalUsers = 0; // Static attribute to keep track of total users

    // Constructor with all details
    public User(String id, String name, String address, String phoneNumber, String username, String password) {
        super(id, name, address, phoneNumber);
        this.username = username;
        this.password = password;
        this.purchaseHistory = new ArrayList<>();
        totalUsers++;
    }

    // Constructor for login with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.purchaseHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void addPurchase(String product) {
        purchaseHistory.add(product);
    }

    @Override
    public void viewPurchaseHistory() {
        System.out.println("Lịch sử mua hàng của " + username + ":");
        if (purchaseHistory.isEmpty()) {
            System.out.println("Không có lịch sử mua hàng.");
        } else {
            for (String purchase : purchaseHistory) {
                System.out.println(purchase);
            }
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Username: " + username);
    }

    public static int getTotalUsers() {
        return totalUsers;
    }

    @Override
    public void performAction() {
        System.out.println(username + " is performing an action.");
    }
}
