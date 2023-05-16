package com.melancholia.educationplatform.user;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.review.Review;
import com.melancholia.educationplatform.permissions.Privilege;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Pattern(regexp = "^(\\+7|8)\\d{10}$",message = "Формат +7XXXXXXXXXX либо 8XXXXXXXXXX")
    @Column(name = "phone", unique = true)
    private String phone;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_privileges",
            joinColumns =
            @JoinColumn(name = "user_id"),
            inverseJoinColumns =
            @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges = new HashSet<>();

    private Boolean locked = false;
    private Boolean enabled = false;

    public void addPrivilege(Privilege privilege){
        privileges.add(privilege);
    }

    public User(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
