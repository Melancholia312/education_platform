package com.melancholia.educationplatform.util;


import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseRepository;
import com.melancholia.educationplatform.course.module.ModuleRepository;
import com.melancholia.educationplatform.course.review.Review;
import com.melancholia.educationplatform.course.review.ReviewRepository;
import com.melancholia.educationplatform.user.permissions.Privilege;
import com.melancholia.educationplatform.user.permissions.PrivilegeRepository;
import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    @Autowired
    private ModuleRepository moduleRepository;

    @PostConstruct
    public void init() {
        initPrivileges();
        initUsers();
        initCourses();
    }


    private void initPrivileges() {

        Privilege privilege1 = new Privilege();
        privilege1.setName("COURSE_1_WRITE");
        privilegeRepository.save(privilege1);
    }

    private void initCourses() {
        User user = userRepository.findByEmail("qwe@mail.ru");
        final Course course = new Course();
        course.setName("qwe1");
        course.setPublished(true);
        course.setDescription("qwe");
        course.setShortDescription("qwe");
        course.addAuthor(user);
        course.setCreationDate(new Date());
        courseRepository.save(course);



    }

    private void initUsers() {
        Privilege privilege1 = privilegeRepository.findByName("COURSE_1_WRITE");

        final User user1 = new User();
        user1.setUsername("qwe");
        user1.setPassword(encoder.encode("123"));
        user1.setEmail("qwe@mail.ru");
        user1.setEnabled(true);
        userRepository.save(user1);
        userRepository.addPermission(user1.getId(), privilege1.getId());
        final User user2 = new User();
        user2.setUsername("qwe1");
        user2.setPassword(encoder.encode("123"));
        user2.setEmail("qwe1@mail.ru");
        user2.setEnabled(true);
        userRepository.save(user2);
    }



}