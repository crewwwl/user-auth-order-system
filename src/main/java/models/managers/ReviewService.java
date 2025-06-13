package managers;

import models.Review;
import java.util.*;
import java.sql.*;

public class ReviewService {
    private Connection connection;
    
    public ReviewService(Connection connection) {
        this.connection = connection;
    }
    
    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, seller_id, car_id, rating, title, comment) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getSellerId());
            if (review.getCarId() != null) {
                stmt.setInt(3, review.getCarId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getTitle());
            stmt.setString(6, review.getComment());
            
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    review.setReviewId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении отзыва: " + e.getMessage());
        }
        return false;
    }
    
    
    public List<Review> getReviewsBySeller(int sellerId, boolean onlyApproved) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE seller_id = ?";
        if (onlyApproved) {
            sql += " AND is_approved = TRUE";
        }
        sql += " ORDER BY date_created DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении отзывов: " + e.getMessage());
        }
        return reviews;
    }
    
    public List<Review> getReviewsByUser(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE user_id = ? ORDER BY date_created DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении отзывов пользователя: " + e.getMessage());
        }
        return reviews;
    }
    
    
    public boolean approveReview(int reviewId) {
        String sql = "UPDATE reviews SET is_approved = TRUE WHERE review_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при одобрении отзыва: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении отзыва: " + e.getMessage());
        }
        return false;
    }
    
    
    public double getAverageRating(int sellerId) {
        String sql = "SELECT AVG(rating) FROM reviews WHERE seller_id = ? AND is_approved = TRUE";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при вычислении рейтинга: " + e.getMessage());
        }
        return 0.0;
    }
    
    
    public int getReviewCount(int sellerId, boolean onlyApproved) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE seller_id = ?";
        if (onlyApproved) {
            sql += " AND is_approved = TRUE";
        }
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при подсчете отзывов: " + e.getMessage());
        }
        return 0;
    }
    
    
    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setSellerId(rs.getInt("seller_id"));
        
        int carId = rs.getInt("car_id");
        if (!rs.wasNull()) {
            review.setCarId(carId);
        }
        
        review.setRating(rs.getInt("rating"));
        review.setTitle(rs.getString("title"));
        review.setComment(rs.getString("comment"));
        review.setDateCreated(rs.getTimestamp("date_created").toLocalDateTime());
        review.setApproved(rs.getBoolean("is_approved"));
        
        return review;
    }
}