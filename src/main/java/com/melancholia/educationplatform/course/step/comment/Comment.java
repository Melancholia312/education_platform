package com.melancholia.educationplatform.course.step.comment;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.step.Step;
import com.melancholia.educationplatform.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Comment")
@Table(name = "comment")
@Data
@NoArgsConstructor
public class Comment {

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
    @JoinColumn(name = "step_id")
    private Step step;


    public Comment(User author, String reviewText, Step step) {
        this.reviewText = reviewText;
        this.author = author;
        this.step = step;
    }

}
