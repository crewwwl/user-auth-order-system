package models.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import models.Review;

public class ReviewService {
    private final Connection conn;

    public ReviewService(Connection conn) {
        this.conn = conn;
    }

   public boolean addReview(Review review) {
    try {
        String sql = "INSERT INTO reviews (user_id, seller_id, car_id, rating, title, comment, date_created, is_approved) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, review.getUserId());
        stmt.setInt(2, review.getSellerId());
        if (review.getCarId() == null) stmt.setNull(3, Types.INTEGER);
        else stmt.setInt(3, review.getCarId());
        stmt.setInt(4, review.getRating());
        stmt.setString(5, review.getTitle());
        stmt.setString(6, review.getComment());
        stmt.setTimestamp(7, Timestamp.valueOf(review.getDateCreated()));
        stmt.setBoolean(8, review.isApproved());

        return stmt.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public List<Review> getReviewsByUser(int userId) {
    List<Review> reviews = new ArrayList<>();
    try {
        String sql = "SELECT * FROM reviews WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Review review = new Review();
            review.setReviewId(rs.getInt("review_id"));
            review.setUserId(rs.getInt("user_id"));
            review.setSellerId(rs.getInt("seller_id"));
            review.setCarId(rs.getObject("car_id", Integer.class));
            review.setRating(rs.getInt("rating"));
            review.setTitle(rs.getString("title"));
            review.setComment(rs.getString("comment"));
            review.setDateCreated(rs.getTimestamp("date_created").toLocalDateTime());
            review.setApproved(rs.getBoolean("is_approved"));
            reviews.add(review);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return reviews;
}


    public List<Review> getReviewsBySeller(int sellerId, boolean approved) {
        List<Review> reviews = new ArrayList<>();
        try {
            String sql = "SELECT * FROM reviews WHERE sellerId = ? AND approved = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sellerId);
            stmt.setBoolean(2, approved);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("id"));
                review.setUserId(rs.getInt("userId"));
                review.setSellerId(rs.getInt("sellerId"));
                review.setCarId(rs.getObject("carId", Integer.class));
                review.setRating(rs.getInt("rating"));
                review.setTitle(rs.getString("title"));
                review.setComment(rs.getString("comment"));
                review.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
                review.setApproved(rs.getBoolean("approved"));
                reviews.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public double getAverageRating(int sellerId) {
        try {
            String sql = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE sellerId = ? AND approved = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getReviewCount(int sellerId, boolean approved) {
        try {
            String sql = "SELECT COUNT(*) AS count FROM reviews WHERE sellerId = ? AND approved = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sellerId);
            stmt.setBoolean(2, approved);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
