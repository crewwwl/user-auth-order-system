package models;

import java.time.LocalDateTime;

public class Seller {
    private int sellerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double rating;
    private int totalSales;
    private boolean verified;
    private LocalDateTime dateRegistered;
    
    public Seller() {}
    
    public Seller(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.rating = 0.0;
        this.totalSales = 0;
        this.verified = false;
        this.dateRegistered = LocalDateTime.now();
    }
    
   
    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }
    
    public String getName() { return name; }
    public void setName(String name) { 
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name should not be empty");
        }
        this.name = name; 
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { 
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Incorrect email adress");
        }
        this.email = email; 
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public double getRating() { return rating; }
    public void setRating(double rating) { 
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.5");
        }
        this.rating = rating; 
    }
    
    public int getTotalSales() { return totalSales; }
    public void setTotalSales(int totalSales) { 
        if (totalSales < 0) {
            throw new IllegalArgumentException("The number of sales should not be negative");
        }
        this.totalSales = totalSales; 
    }
    
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    
    public LocalDateTime getDateRegistered() { return dateRegistered; }
    public void setDateRegistered(LocalDateTime dateRegistered) { this.dateRegistered = dateRegistered; }
    
    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", rating=" + rating +
                ", verified=" + verified +
                '}';
    }
}