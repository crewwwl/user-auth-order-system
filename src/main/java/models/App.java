package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;

import models.managers.ReviewService;
import models.managers.UserService;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=CarMarketPlaceDB;encrypt=false";
            String user = "crewwwl";
            String password = "Ansar0102";

            Connection conn = DriverManager.getConnection(url, user, password);

            UserService userService = new UserService(conn);
            ReviewService reviewService = new ReviewService(conn);
            OrderService orderService = new OrderService(conn); 

            System.out.println("=== Registration Test ===");
            boolean registered = userService.registerUser("testuser3", "test3@example.com", "Ansar0102");
            System.out.println(registered ? "✅ User registered successfully" : "❌ User registration failed");

            System.out.println("\n=== Login Test ===");
            boolean loggedIn = userService.login("test3@example.com", "Ansar0102");
            System.out.println(loggedIn ? "✅ User logged in successfully" : "❌ User login failed");

            System.out.println("\n=== Add Review Test ===");
            Review review = new Review();
            review.setUserId(3);  
            review.setSellerId(2);
            review.setCarId(null);
            review.setRating(5);
            review.setTitle("Excellent");
            review.setComment("Perfect transaction");
            review.setDateCreated(LocalDateTime.now());
            review.setApproved(false);

            boolean reviewAdded = reviewService.addReview(review);
            System.out.println(reviewAdded ? "✅ Review added successfully" : "❌ Review adding failed");

            System.out.println("\n=== Fetch User Reviews ===");
            List<Review> userReviews = reviewService.getReviewsByUser(3);
            for (Review r : userReviews) {
                System.out.println("Review from user #" + r.getUserId() + ": " + r.getTitle());
            }

            System.out.println("\n=== Add Order Test ===");
            Order order = new Order();
            order.setUserId(3);           
            order.setProductId(1);        
            order.setQuantity(2);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("Pending");

            boolean orderPlaced = orderService.placeOrder(order);

            System.out.println(orderPlaced ? "✅ Order added successfully" : "❌ Order adding failed");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
