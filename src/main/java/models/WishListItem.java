package models;
import java.util.Date;

public class WishListItem {
    private int wishListId;
    private int userId;
    private int carId;
    private Date dateAdded;
    
    public WishListItem() {}
    
    public WishListItem(int userId, int carId) {
        this.userId = userId;
        this.carId = carId;
        this.dateAdded = new Date();
    }
    
    // Getters and Setters
    public int getWishListId() { return wishListId; }
    public void setWishListId(int wishListId) { this.wishListId = wishListId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    
    public Date getDateAdded() { return dateAdded; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }
}