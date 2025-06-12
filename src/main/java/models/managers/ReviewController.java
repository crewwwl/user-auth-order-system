package managers;

import models.Review;
public class ReviewController {
    private ReviewService reviewService;
    private SellerService sellerService;
    
    public ReviewController(ReviewService reviewService, SellerService sellerService) {
        this.reviewService = reviewService;
        this.sellerService = sellerService;
    }
    
    
    public boolean addReviewAndUpdateRating(Review review) {
        boolean success = reviewService.addReview(review);
        if (success) {
            
            sellerService.updateSellerRating(review.getSellerId());
        }
        return success;
    }
    
    
    public boolean approveReviewAndUpdateRating(int reviewId, int sellerId) {
        boolean success = reviewService.approveReview(reviewId);
        if (success) {
            sellerService.updateSellerRating(sellerId);
        }
        return success;
    }
    
    
    public boolean deleteReviewAndUpdateRating(int reviewId, int sellerId) {
        boolean success = reviewService.deleteReview(reviewId);
        if (success) {
            sellerService.updateSellerRating(sellerId);
        }
        return success;
    }
}