package com.melancholia.educationplatform.course.review;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseService;
import com.melancholia.educationplatform.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final CourseService courseService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/review/add/{id}")
    public String addReview(@PathVariable("id") long id,
                            @ModelAttribute Review review,
                            Authentication authentication){
        Course course = courseService.findPublishedCourseById(id);
        review.setCourse(course);
        review.setAuthor(((User) authentication.getPrincipal()).getUsername());
        reviewService.reviewSave(review);
        return String.format("redirect:/course/%s/promo", id);
    }

}
