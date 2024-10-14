package Nguoi;

import java.io.Serializable;

public abstract class Nguoi implements Serializable {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;

    public Nguoi() {}

    public Nguoi(String id, String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Phương thức hiển thị thông tin
    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Address: " + address + ", Phone: " + phoneNumber);
    }

    // Abstract method to be implemented in subclasses
    public abstract void performAction();
}
