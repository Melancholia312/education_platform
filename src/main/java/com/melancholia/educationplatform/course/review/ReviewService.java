package com.melancholia.educationplatform.course.review;

import com.melancholia.educationplatform.course.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void reviewSave(Review review){
        reviewRepository.save(review);
    }

}
