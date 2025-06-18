package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;

import models.managers.ReviewService;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=CarMarketPlaceDB;encrypt=false";
            String user = "crewwwl";
            String password = "Ansar0102";

            Connection conn = DriverManager.getConnection(url, user, password);
            ReviewService service = new ReviewService(conn);

            Review review = new Review();
            review.setUserId(1);
            review.setSellerId(2);
            review.setCarId(null);
            review.setRating(5);
            review.setTitle("Excellent");
            review.setComment("Perfect transaction");
            review.setDateCreated(LocalDateTime.now());
            review.setApproved(false);

            boolean success = service.addReview(review);
            System.out.println(success ? "✅ Review added" : "❌ Review failed");

            List<Review> userReviews = service.getReviewsByUser(1);
            for (Review r : userReviews) {
                System.out.println("Review from user #" + r.getUserId() + ": " + r.getTitle());
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
