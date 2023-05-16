package com.melancholia.educationplatform.course;


import com.melancholia.educationplatform.review.Review;
import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.step.Step;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity(name = "Course")
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "course_id",
            updatable = false
    )
    private long id;

    @Column(name = "id", nullable = false)
    private String name;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Step> steps;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Review> reviews;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "is_published")
    private boolean isPublished;

    public Course(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
