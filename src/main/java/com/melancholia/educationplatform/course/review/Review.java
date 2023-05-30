package com.melancholia.educationplatform.course.review;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Review")
@Table(name = "review")
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "review_id",
            updatable = false
    )
    private Long id;

    @Column(name = "reviewText", nullable = false)
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "stars")
    @Min(value=1)
    @Max(value=5)
    private int stars;

    public Review(User author, String reviewText, Course course, int stars) {
        this.reviewText = reviewText;
        this.stars = stars;
        this.author = author;
        this.course = course;
    }

}
