package com.melancholia.educationplatform.step;

import com.melancholia.educationplatform.course.Course;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "Step")
@Table(name = "step")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "step_id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
