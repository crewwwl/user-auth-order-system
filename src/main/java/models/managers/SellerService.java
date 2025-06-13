package managers;

import models.Seller;
import models.SellerProfile;
import models.Review;
import java.util.*;
import java.sql.*;

public class SellerService {
    private Connection connection;
    private ReviewService reviewService;
    
    public SellerService(Connection connection) {
        this.connection = connection;
        this.reviewService = new ReviewService(connection);
    }
    
    
    public boolean addSeller(Seller seller) {
        String sql = "INSERT INTO sellers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setString(3, seller.getPhone());
            stmt.setString(4, seller.getAddress());
            
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    seller.setSellerId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении продавца: " + e.getMessage());
        }
        return false;
    }
    
    
    public Seller getSellerById(int sellerId) {
        String sql = "SELECT * FROM sellers WHERE seller_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSeller(rs);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении продавца: " + e.getMessage());
        }
        return null;
    }
    
    
    public boolean updateSeller(Seller seller) {
        String sql = "UPDATE sellers SET name = ?, email = ?, phone = ?, address = ? WHERE seller_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setString(3, seller.getPhone());
            stmt.setString(4, seller.getAddress());
            stmt.setInt(5, seller.getSellerId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении продавца: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean verifySeller(int sellerId) {
        String sql = "UPDATE sellers SET verified = TRUE WHERE seller_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при верификации продавца: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean updateSellerRating(int sellerId) {
        double avgRating = reviewService.getAverageRating(sellerId);
        
        String sql = "UPDATE sellers SET rating = ? WHERE seller_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, avgRating);
            stmt.setInt(2, sellerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении рейтинга: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean incrementSales(int sellerId) {
        String sql = "UPDATE sellers SET total_sales = total_sales + 1 WHERE seller_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sellerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при увеличении продаж: " + e.getMessage());
        }
        return false;
    }
    
    
    public List<Seller> getTopSellersByRating(int limit) {
        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT * FROM sellers WHERE verified = TRUE ORDER BY rating DESC, total_sales DESC LIMIT ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seller seller = mapResultSetToSeller(rs);
                sellers.add(seller);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении топ продавцов: " + e.getMessage());
        }
        return sellers;
    }
    
    
    public List<Seller> getAllSellers() {
        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT * FROM sellers ORDER BY name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Seller seller = mapResultSetToSeller(rs);
                sellers.add(seller);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех продавцов: " + e.getMessage());
        }
        return sellers;
    }
    
    
    public List<Seller> searchSellersByName(String name) {
        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT * FROM sellers WHERE name LIKE ? ORDER BY rating DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seller seller = mapResultSetToSeller(rs);
                sellers.add(seller);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске продавцов: " + e.getMessage());
        }
        return sellers;
    }
    
    
    public SellerProfile getSellerProfile(int sellerId) {
        Seller seller = getSellerById(sellerId);
        if (seller == null) {
            return null;
        }
        
        List<Review> reviews = reviewService.getReviewsBySeller(sellerId, true);
        int totalReviews = reviewService.getReviewCount(sellerId, true);
        
        return new SellerProfile(seller, reviews, totalReviews);
    }
    
    
    private Seller mapResultSetToSeller(ResultSet rs) throws SQLException {
        Seller seller = new Seller();
        seller.setSellerId(rs.getInt("seller_id"));
        seller.setName(rs.getString("name"));
        seller.setEmail(rs.getString("email"));
        seller.setPhone(rs.getString("phone"));
        seller.setAddress(rs.getString("address"));
        seller.setRating(rs.getDouble("rating"));
        seller.setTotalSales(rs.getInt("total_sales"));
        seller.setVerified(rs.getBoolean("verified"));
        seller.setDateRegistered(rs.getTimestamp("date_registered").toLocalDateTime());
        
        return seller;
    }
}