package Nguoi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> userList = new ArrayList<>();
    private List<Manager> managerList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    public UserManager() {
        loadUsersFromFile("users.txt");
        loadManagersFromFile("managers.txt");
    }

    // Register a new user
    public void registerUser(String id, String name, String address, String phoneNumber, String username, String password) {
        User user = new User(id, name, address, phoneNumber, username, password);
        userList.add(user);
        saveUsersToFile("users.txt");
        writeUserDetailsToFile(user);
        System.out.println("Đăng ký thành công.");
    }

    private void writeUserDetailsToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_registration.txt", true))) {
            writer.write("ID của user: " + user.getId() + ", tên: " + user.getName() + ", địa chỉ: " + user.getAddress() +
                    ", số điện thoại: " + user.getPhoneNumber() + ", username: " + user.getUsername() +
                    ", password: " + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi thông tin người dùng vào file: " + e.getMessage());
        }
    }

    // Register a new manager
    public void registerManager(String id, String name, String address, String phoneNumber, String username, String password) {
        Manager manager = new Manager(id, name, address, phoneNumber, username, password);
        managerList.add(manager);
        saveManagersToFile("managers.txt");
        writeManagerDetailsToFile(manager);
        System.out.println("Quản lý đăng ký thành công.");
    }

    private void writeManagerDetailsToFile(Manager manager) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("manager_registration.txt", true))) {
            writer.write("ID của manager: " + manager.getId() + ", tên: " + manager.getName() + ", địa chỉ: " + manager.getAddress() +
                    ", số điện thoại: " + manager.getPhoneNumber() + ", username: " + manager.getUsername() +
                    ", password: " + manager.getPassword());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi thông tin quản lý vào file: " + e.getMessage());
        }
    }

    // User login
    public User loginUser(String username, String password) {
        for (User user : userList) {
            if (user.login(username, password)) {
                System.out.println("Đăng nhập thành công.");
                return user;
            }
        }
        System.out.println("Tên đăng nhập hoặc mật khẩu không đúng.");
        return null;
    }

    // Manager login
    public Manager loginManager(String username, String password) {
        for (Manager manager : managerList) {
            if (manager.login(username, password)) {
                System.out.println("Quản lý đăng nhập thành công.");
                return manager;
            }
        }
        System.out.println("Tên đăng nhập hoặc mật khẩu không đúng.");
        return null;
    }

    // User logout
    public void logoutUser(User user) {
        // Note: It's common not to remove a user from the list on logout,
        // but simply to handle the session state.
        System.out.println("Đã đăng xuất người dùng.");
    }

    // Manager logout
    public void logoutManager(Manager manager) {
        // Similar to users, typically you don't remove managers from the list on logout.
        System.out.println("Đã đăng xuất quản lý.");
    }

    // Load users from file
    private void loadUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                userList.add(user);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file người dùng: " + e.getMessage());
        }
    }

    // Load managers from file
    private void loadManagersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Manager manager = new Manager(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                managerList.add(manager);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file quản lý: " + e.getMessage());
        }
    }

    // Save users to file
    private void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : userList) {
                writer.write(user.getId() + "," + user.getName() + "," + user.getAddress() + "," +
                        user.getPhoneNumber() + "," + user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file người dùng: " + e.getMessage());
        }
    }

    // Save managers to file
    private void saveManagersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Manager manager : managerList) {
                writer.write(manager.getId() + "," + manager.getName() + "," + manager.getAddress() + "," +
                        manager.getPhoneNumber() + "," + manager.getUsername() + "," + manager.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file quản lý: " + e.getMessage());
        }
    }

    // View all products
    public void viewProducts() {
        System.out.println("Danh sách sản phẩm:");
        for (Product product : productList) {
            product.displayProduct();
        }
    }

    // Add a new product or update existing product quantity
    public void addProduct(Scanner scanner) {
        System.out.println("Thêm sản phẩm:");
        System.out.print("Nhập ID sản phẩm: ");
        String productId = scanner.next();
        scanner.nextLine(); // Clear the newline character

        Product existingProduct = productList.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            System.out.print("Nhập số lượng muốn thêm: ");
            int quantityToAdd = getIntInput(scanner, "Giá trị không hợp lệ. Vui lòng nhập lại số lượng: ");
            existingProduct.setQuantity(existingProduct.getQuantity() + quantityToAdd);
            System.out.println("Đã cập nhật số lượng sản phẩm thành công.");
        } else {
            System.out.print("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine();
            System.out.print("Nhập giá sản phẩm: ");
            double price = getDoubleInput(scanner, "Giá trị không hợp lệ. Vui lòng nhập lại giá sản phẩm: ");
            System.out.print("Nhập số lượng sản phẩm: ");
            int quantity = getIntInput(scanner, "Giá trị không hợp lệ. Vui lòng nhập lại số lượng: ");

            productList.add(new Product(productId, productName, price, quantity));
            System.out.println("Đã thêm sản phẩm thành công.");
        }
    }

    // Remove a product by ID
    public void removeProduct(Scanner scanner) {
        System.out.println("Xóa sản phẩm:");
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String productId = scanner.next();

        boolean removed = productList.removeIf(product -> product.getProductId().equals(productId));
        if (removed) {
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Sản phẩm không tồn tại.");
        }
    }

    // Utility method for inputting integers
    private int getIntInput(Scanner scanner, String errorMessage) {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
            }
        }
        return value;
    }

    // Utility method for inputting doubles
    private double getDoubleInput(Scanner scanner, String errorMessage) {
        double value;
        while (true) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print(errorMessage);
            }
        }
        return value;
    }
}
