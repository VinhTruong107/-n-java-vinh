import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Nguoi.User;
import Nguoi.UserManager;
import Nguoi.Manager;

public class Main3 {
    private static List<Product> productList = new ArrayList<>();
    private static UserManager userManager = new UserManager();

    public static void main(String[] args) {
        loadProductsFromFile("products.txt");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Đăng ký User");
            System.out.println("2. Đăng ký Manager");
            System.out.println("3. Đăng nhập User");
            System.out.println("4. Đăng nhập Manager");
            System.out.println("5. Thoát");
            System.out.print("Chọn một chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    registerManager(scanner);
                    break;
                case 3:
                    User loggedInUser = loginUser(scanner);
                    if (loggedInUser != null) {
                        userMenu(scanner, loggedInUser);
                    }
                    break;
                case 4:
                    Manager loggedInManager = loginManager(scanner);
                    if (loggedInManager != null) {
                        managerMenu(scanner, loggedInManager);
                    }
                    break;
                case 5:
                    saveProductsToFile("products_output.txt");
                    System.out.println("Đã lưu thông tin sản phẩm ra file và thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Nhập ID của User: ");
        String id = scanner.nextLine();
        System.out.print("Nhập tên: ");
        String name = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        String address = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Nhập username: ");
        String username = scanner.nextLine();
        System.out.print("Nhập password: ");
        String password = scanner.nextLine();
        userManager.registerUser(id, name, address, phoneNumber, username, password);
    }

    private static void registerManager(Scanner scanner) {
        System.out.print("Nhập ID của Manager: ");
        String id = scanner.nextLine();
        System.out.print("Nhập tên: ");
        String name = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        String address = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Nhập username: ");
        String username = scanner.nextLine();
        System.out.print("Nhập password: ");
        String password = scanner.nextLine();
        userManager.registerManager(id, name, address, phoneNumber, username, password);
    }

    private static User loginUser(Scanner scanner) {
        System.out.print("Nhập username: ");
        String username = scanner.nextLine();
        System.out.print("Nhập password: ");
        String password = scanner.nextLine();
        return userManager.loginUser(username, password);
    }

    private static Manager loginManager(Scanner scanner) {
        System.out.print("Nhập username của Manager: ");
        String username = scanner.nextLine();
        System.out.print("Nhập password: ");
        String password = scanner.nextLine();
        return userManager.loginManager(username, password);
    }

    private static void userMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. Mua hàng");
            System.out.println("2. Xem lịch sử mua hàng");
            System.out.println("3. Đăng xuất");
            System.out.print("Chọn một chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userBuyProduct(scanner, user);
                    break;
                case 2:
                    user.viewPurchaseHistory();
                    break;
                case 3:
                    userManager.logoutUser(user);
                    return; // Đăng xuất và quay lại menu chính
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void userBuyProduct(Scanner scanner, User user) {
        System.out.println("Chức năng mua hàng:");
        viewProducts();
        System.out.print("Nhập ID sản phẩm cần mua: ");
        String productId = scanner.nextLine();
        System.out.print("Nhập số lượng cần mua: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (Product product : productList) {
            if (product.getProductId().equals(productId) && product.getQuantity() >= quantity) {
                product.setQuantity(product.getQuantity() - quantity);
                user.addPurchase("Mua sản phẩm: " + product.getProductName() + " - Số lượng: " + quantity);
                System.out.println("Mua hàng thành công!");
                return;
            }
        }
        System.out.println("Sản phẩm không đủ số lượng hoặc không tồn tại.");
    }

    private static void managerMenu(Scanner scanner, Manager manager) {
        while (true) {
            System.out.println("Manager Menu:");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Đăng xuất");
            System.out.print("Chọn một chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addProduct(scanner);
                    break;
                case 3:
                    removeProduct(scanner);
                    break;
                case 4:
                    userManager.logoutManager(manager);
                    return; // Đăng xuất và quay lại menu chính
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void viewProducts() {
        System.out.println("Danh sách sản phẩm:");
        for (Product product : productList) {
            System.out.println("ID: " + product.getProductId() + " - Tên: " + product.getProductName() +
                    " - Giá: " + product.getPrice() + " - Số lượng: " + product.getQuantity());
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm: ");
        String productId = scanner.nextLine();
        System.out.print("Nhập tên sản phẩm: ");
        String productName = scanner.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        double price = scanner.nextDouble();
        System.out.print("Nhập số lượng sản phẩm: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        productList.add(new Product(productId, productName, price, quantity));
        System.out.println("Đã thêm sản phẩm thành công.");
    }

    private static void removeProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String productId = scanner.nextLine();

        if (productList.removeIf(product -> product.getProductId().equals(productId))) {
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID đã nhập.");
        }
    }

    private static void loadProductsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String productId = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                int quantity = Integer.parseInt(parts[3]);
                productList.add(new Product(productId, productName, price, quantity));
            }
            System.out.println("Đã tải sản phẩm từ file thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi tải sản phẩm từ file: " + e.getMessage());
        }
    }

    private static void saveProductsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Product product : productList) {
                writer.write(product.getProductId() + "," + product.getProductName() + "," +
                        product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
            System.out.println("Đã lưu sản phẩm vào file thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu sản phẩm vào file: " + e.getMessage());
        }
    }
}
