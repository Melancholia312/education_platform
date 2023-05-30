package com.melancholia.educationplatform.course;


import com.melancholia.educationplatform.course.module.Module;
import com.melancholia.educationplatform.course.review.Review;
import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.course.step.Step;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "comments_allowed")
    private boolean commentsAllowed;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Module> modules;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_authors",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> authors = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Review> reviews;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "is_published")
    private boolean isPublished = false;


    public void addAuthor(User user){
        authors.add(user);
    }
}
