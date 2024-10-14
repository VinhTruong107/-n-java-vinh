import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Nguoi.User;
import Nguoi.UserManager;
import Nguoi.Manager;

public class Main1 {
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
            scanner.nextLine(); // Consume newline

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
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userBuyProduct(scanner, user);
                    break;
                case 2:
                    user.viewPurchaseHistory();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void managerMenu(Scanner scanner, Manager manager) {
        while (true) {
            System.out.println("Manager Menu:");
            System.out.println("1. Xem hàng hóa");
            System.out.println("2. Xem người đã mua hàng");
            System.out.println("3. Thêm sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Đăng xuất");
            System.out.print("Chọn một chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    manager.viewPurchasedUsers(userManager.getUsers());
                    break;
                case 3:
                    addProduct(scanner);
                    break;
                case 4:
                    removeProduct(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
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
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    private static void saveProductsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Product product : productList) {
                writer.write(product.getProductId() + "," + product.getProductName() + "," +
                        product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    private static void userBuyProduct(Scanner scanner, User user) {
        System.out.println("Chức năng mua hàng:");
        viewProducts();
        System.out.print("Nhập ID sản phẩm cần mua: ");
        String productId = scanner.next();
        System.out.print("Nhập số lượng cần mua: ");
        int quantity = scanner.nextInt();

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

    private static void viewProducts() {
        System.out.println("Danh sách sản phẩm:");
        for (Product product : productList) {
            product.displayProduct();
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.println("Thêm sản phẩm:");
        System.out.print("Nhập ID sản phẩm: ");
        String productId = scanner.next();
        scanner.nextLine(); // Đọc dòng còn lại để loại bỏ newline
    
        // Tìm sản phẩm trong danh sách
        Product existingProduct = productList.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    
        if (existingProduct != null) {
            // Nếu sản phẩm đã tồn tại, tăng số lượng
            System.out.print("Nhập số lượng muốn thêm: ");
            int quantityToAdd = 0;
            while (true) {
                try {
                    quantityToAdd = Integer.parseInt(scanner.nextLine());
                    break; // Thoát khỏi vòng lặp nếu nhập hợp lệ
                } catch (NumberFormatException e) {
                    System.out.print("Giá trị không hợp lệ. Vui lòng nhập lại số lượng: ");
                }
            }
            existingProduct.setQuantity(existingProduct.getQuantity() + quantityToAdd);
            System.out.println("Đã cập nhật số lượng sản phẩm thành công.");
        } else {
            // Nếu sản phẩm không tồn tại, yêu cầu thêm thông tin sản phẩm mới
            System.out.print("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine(); // Sử dụng nextLine() để đọc tên sản phẩm có khoảng trắng
            System.out.print("Nhập giá sản phẩm: ");
            double price = 0;
            while (true) {
                try {
                    price = Double.parseDouble(scanner.nextLine());
                    break; // Thoát khỏi vòng lặp nếu nhập hợp lệ
                } catch (NumberFormatException e) {
                    System.out.print("Giá trị không hợp lệ. Vui lòng nhập lại giá sản phẩm: ");
                }
            }
            System.out.print("Nhập số lượng sản phẩm: ");
            int quantity = 0;
            while (true) {
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                    break; // Thoát khỏi vòng lặp nếu nhập hợp lệ
                } catch (NumberFormatException e) {
                    System.out.print("Giá trị không hợp lệ. Vui lòng nhập lại số lượng: ");
                }
            }
    
            productList.add(new Product(productId, productName, price, quantity));
            System.out.println("Đã thêm sản phẩm thành công.");
        }
    }
    
    

    private static void removeProduct(Scanner scanner) {
        System.out.println("Xóa sản phẩm:");
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String productId = scanner.next();

        productList.removeIf(product -> product.getProductId().equals(productId));
        System.out.println("Đã xóa sản phẩm thành công.");
    }
}
