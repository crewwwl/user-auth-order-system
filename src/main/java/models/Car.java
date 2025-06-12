package models;

public class Car {
    private int carId;
    private String make;
    private String model;
    private int year;
    private double price;
    private String condition; // "NEW" or "USED"
    private String description;
    private String imageUrl;
    private int sellerId;
    private boolean isActive;
    
    // Constructors
    public Car() {}
    
    public Car(String make, String model, int year, double price, String condition, 
               String description, String imageUrl, int sellerId) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sellerId = sellerId;
        this.isActive = true;
    }
    
    // Getters and Setters
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", condition='" + condition + '\'' +
                '}';
    }
}