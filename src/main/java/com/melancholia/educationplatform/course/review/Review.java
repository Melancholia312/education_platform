package com.melancholia.educationplatform.course.review;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Review")
@Table(name = "review")
@Data
@NoArgsConstructor
@EqualsAndHashCode
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

    @Column(name = "author", nullable = false)
    private String author;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "stars")
    @Min(value=1)
    @Max(value=5)
    private int stars;

}
