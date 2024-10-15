package Nguoi;

import java.util.ArrayList;

public class Manager extends User implements InfoDisplayable {
    // Constructor with complete details
    public Manager(String id, String name, String address, String phoneNumber, String username, String password) {
        super(id, name, address, phoneNumber, username, password);
    }

    public void viewPurchasedUsers(ArrayList<User> users) {
        System.out.println("Purchased Users:");
        for (User user : users) {
            System.out.println("User: " + user.getUsername());
            user.viewPurchaseHistory();
        }
    }

    @Override
    public void performAction() {
        System.out.println(getUsername() + " is managing users.");
    }
}
