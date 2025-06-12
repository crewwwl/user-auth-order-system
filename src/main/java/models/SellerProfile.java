package models;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SellerProfile {
    private Seller seller;
    private List<Review> reviews;
    private int totalReviews;
    
    public SellerProfile(Seller seller, List<Review> reviews, int totalReviews) {
        this.seller = seller;
        this.reviews = reviews;
        this.totalReviews = totalReviews;
    }
    
    
    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
    
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    
    public int getTotalReviews() { return totalReviews; }
    public void setTotalReviews(int totalReviews) { this.totalReviews = totalReviews; }
    
    
    public Map<Integer, Integer> getRatingDistribution() {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }
        
        for (Review review : reviews) {
            int rating = review.getRating();
            distribution.put(rating, distribution.get(rating) + 1);
        }
        
        return distribution;
    }
    
    @Override
    public String toString() {
        return "SellerProfile{" +
                "seller=" + seller +
                ", totalReviews=" + totalReviews +
                ", reviewsCount=" + reviews.size() +
                '}';
    }
}