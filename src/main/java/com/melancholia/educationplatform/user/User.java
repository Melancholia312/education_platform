package com.melancholia.educationplatform.user;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.review.Review;
import com.melancholia.educationplatform.user.permissions.Privilege;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "User")
@Table(name = "user")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
