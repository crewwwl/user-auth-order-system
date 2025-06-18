package models.managers;

import models.Review;
import java.util.List;

public class SellerService {
    private ReviewService reviewService;

    public SellerService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public double getAverageRating(int sellerId) {
        return reviewService.getAverageRating(sellerId);
    }

    public int getTotalReviewCount(int sellerId) {
        return reviewService.getReviewCount(sellerId, true);
    }

    public List<Review> getReviews(int sellerId) {
        return reviewService.getReviewsBySeller(sellerId, true);
    }
}
