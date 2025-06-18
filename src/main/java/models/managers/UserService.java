package models.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;

public class UserService {
    private final Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }

    public boolean registerUser(String username, String email, String password) {
        try {
            if (username == null || email == null || password == null || username.isBlank() || email.isBlank() || password.isBlank()) {
                System.out.println("❌ All fields are required");
                return false;
            }

            String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("❌ Email already registered");
                return false;
            }

            String hashedPassword = hashPassword(password);

            String sql = "INSERT INTO users (username, email, password_hash, date_registered, is_active) VALUES (?, ?, ?, GETDATE(), 1)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean login(String email, String password) {
        try {
            if (email == null || password == null || email.isBlank() || password.isBlank()) {
                System.out.println("❌ Email and password are required");
                return false;
            }

            String sql = "SELECT password_hash FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String inputHash = hashPassword(password);

                if (storedHash.equals(inputHash)) {
                    return true;  
                } else {
                    System.out.println("❌ Incorrect password");
                    return false;
                }
            } else {
                System.out.println("❌ User not found");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
