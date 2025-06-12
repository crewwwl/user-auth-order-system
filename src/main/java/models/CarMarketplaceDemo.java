import managers.CarListingManager;
import managers.WishListManager;
import models.Car;
import models.WishListItem;
import java.math.BigDecimal;
import java.util.*;

public class CarMarketplaceDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Car Marketplace Demo ===\n");
        
        // Get manager instances
        CarListingManager carManager = CarListingManager.getInstance();
        WishListManager wishListManager = WishListManager.getInstance();
        
        // Demo data
        demoCarListingManagement(carManager);
        demoWishListManagement(wishListManager, carManager);
        demoSearchAndFilter(carManager);
        demoStatistics(carManager, wishListManager);
        
        // Cleanup
        carManager.close();
        wishListManager.close();
        
        System.out.println("\n=== Demo Completed ===");
    }
    
    private static void demoCarListingManagement(CarListingManager carManager) {
        System.out.println("1. === CAR LISTING MANAGEMENT DEMO ===");
        
        // Add new cars
        System.out.println("\n--- Adding New Cars ---");
        
        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setModel("Camry");
        car1.setYear(2024);
        car1.setPrice(new BigDecimal("35000.00"));
        car1.setCondition("NEW");
        car1.setSellerId(1);
        car1.setMileage(0);
        car1.setColor("White");
        car1.setFuelType("Gasoline");
        car1.setTransmission("Automatic");
        car1.setDescription("Brand new Toyota Camry 2024 with all features");
        
        boolean added1 = carManager.addCar(car1);
        System.out.println("Toyota Camry added: " + added1);
        
        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setModel("Civic");
        car2.setYear(2020);
        car2.setPrice(new BigDecimal("22000.00"));
        car2.setCondition("USED");
        car2.setSellerId(2);
        car2.setMileage(45000);
        car2.setColor("Blue");
        car2.setFuelType("Gasoline");
        car2.setTransmission("Manual");
        car2.setDescription("Well-maintained Honda Civic with low mileage");
        
        boolean added2 = carManager.addCar(car2);
        System.out.println("Honda Civic added: " + added2);
        
        Car car3 = new Car();
        car3.setBrand("Tesla");
        car3.setModel("Model 3");
        car3.setYear(2023);
        car3.setPrice(new BigDecimal("45000.00"));
        car3.setCondition("NEW");
        car3.setSellerId(1);
        car3.setMileage(0);
        car3.setColor("Black");
        car3.setFuelType("Electric");
        car3.setTransmission("Automatic");
        car3.setDescription("Tesla Model 3 with autopilot features");
        
        boolean added3 = carManager.addCar(car3);
        System.out.println("Tesla Model 3 added: " + added3);
        
        // Display all cars
        System.out.println("\n--- All Cars in System ---");
        List<Car> allCars = carManager.getAllCars();
        displayCars(allCars);
        
        // Update a car
        System.out.println("\n--- Updating Car Price ---");
        if (!allCars.isEmpty()) {
            Car carToUpdate = allCars.get(0);
            BigDecimal oldPrice = carToUpdate.getPrice();
            carToUpdate.setPrice(new BigDecimal("33000.00"));
            boolean updated = carManager.updateCar(carToUpdate);
            System.out.println("Updated car price from $" + oldPrice + " to $" + carToUpdate.getPrice() + ": " + updated);
        }
        
        // Test validation with invalid car
        System.out.println("\n--- Testing Validation ---");
        Car invalidCar = new Car();
        invalidCar.setBrand("");  // Invalid empty brand
        invalidCar.setModel("Test");
        invalidCar.setYear(1800);  // Invalid year
        invalidCar.setPrice(new BigDecimal("-1000"));  // Invalid negative price
        invalidCar.setCondition("BROKEN");  // Invalid condition
        invalidCar.setSellerId(-1);  // Invalid seller ID
        
        boolean addedInvalid = carManager.addCar(invalidCar);
        System.out.println("Invalid car added (should be false): " + addedInvalid);
    }
    
    private static void demoWishListManagement(WishListManager wishListManager, CarListingManager carManager) {
        System.out.println("\n\n2. === WISH LIST MANAGEMENT DEMO ===");
