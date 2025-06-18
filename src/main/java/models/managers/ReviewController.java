package models.managers;

import models.Review;
import java.util.List;

public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public boolean submitReview(Review review) {
        return reviewService.addReview(review);
    }

    public List<Review> listReviewsBySeller(int sellerId) {
        return reviewService.getReviewsBySeller(sellerId, true);
    }
}