package com.melancholia.educationplatform.util;


import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseRepository;
import com.melancholia.educationplatform.review.Review;
import com.melancholia.educationplatform.review.ReviewRepository;
import com.melancholia.educationplatform.permissions.Privilege;
import com.melancholia.educationplatform.permissions.PrivilegeRepository;
import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PostConstruct
    public void init() {
        initCourses();
        initPrivileges();
        initUsers();
        initReviews();
    }

    private void initReviews() {
        Review review1 = new Review("qwe1", 1);
        Review review2 = new Review("qwe2", 2);
        Review review3 = new Review("qwe3", 3);
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
    }

    private void initPrivileges() {
        /*Privilege privilege1 = new Privilege();
        privilege1.setName("REVIEW_1_READ");
        privilegeRepository.save(privilege1);*/
    }

    private void initCourses() {
        final Course course = new Course("IT", 0, "qwe");
        courseRepository.save(course);
        final Course course1 = new Course("IT1", 0, "qwe");
        courseRepository.save(course1);
    }

    private void initUsers() {

        Privilege privilege1 = privilegeRepository.findByName("REVIEW_1_READ");

        final User user1 = new User();
        user1.setUsername("qwe");
        user1.setPassword(encoder.encode("123"));
        user1.setEmail("qwe@mail.ru");
        user1.setEnabled(true);
        //user1.addPrivilege(privilege1);
        userRepository.save(user1);

        final User user2 = new User();
        user2.setUsername("qwe1");
        user2.setPassword(encoder.encode("123"));
        user2.setEmail("qwe1@mail.ru");
        user2.setEnabled(true);
        userRepository.save(user2);
    }



}