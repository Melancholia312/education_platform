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
    private PasswordEncoder encoder;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PostConstruct
    public void init() {
        initPrivileges();
        initUsers();
        initCourses();
    }


    private void initPrivileges() {

        Privilege admin = new Privilege();
        admin.setName("ROLE_ADMIN");
        privilegeRepository.save(admin);
        Privilege coursePrivilege = new Privilege();
        coursePrivilege.setName("COURSE_1_WRITE");
        privilegeRepository.save(coursePrivilege);
    }

    private void initCourses() {
        User user = userRepository.findByEmail("admin@mail.ru");
        final Course course = new Course();
        course.setName("Курс по Java Spring");
        course.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Integer accumsan nisi nisl, in eleifend felis fringilla ac. Donec ac egestas nunc. " +
                "Fusce pharetra dui nec nulla faucibus dignissim. Suspendisse vestibulum, lorem eget consequat hendrerit, " +
                "lectus nisi lacinia tellus, quis rutrum mauris ante id nunc. Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Fusce non euismod magna, in tempus lectus. Vivamus ac luctus libero. Suspendisse suscipit orci in dolor porttitor dapibus. " +
                "Aliquam sollicitudin tempus purus vitae congue. Nulla mattis non est porta ultricies.\n" +
                "\n" +
                "Mauris ullamcorper convallis tellus. Nullam vestibulum sem non eros cursus, " +
                "eget efficitur ligula eleifend. Aenean elementum lacinia nisl sed rhoncus. Donec quis vulputate nunc. " +
                "Aenean in dui rhoncus, dictum tortor at, placerat odio. Fusce tempor ornare ante, vitae aliquam nulla feugiat vehicula. " +
                "Suspendisse id enim eu neque semper ultrices. Morbi id arcu nec ex scelerisque aliquet ac vel massa.\n" +
                "\n" +
                "Aliquam cursus urna at quam aliquet efficitur. Morbi quis varius ligula, at rutrum ipsum. Morbi a justo purus. " +
                "Morbi condimentum lectus nec auctor tristique. Cras eu scelerisque eros. Integer laoreet lectus quis mattis hendrerit. " +
                "Fusce tempus tellus vitae facilisis vulputate. Aliquam aliquet imperdiet semper. Donec et erat non odio tempus bibendum " +
                "porttitor eget neque. Aliquam cursus elit nisi, vel consectetur erat feugiat eget. Nulla porta eu diam nec fringilla. " +
                "Etiam mauris magna, egestas quis nisl non, auctor varius neque. Cras quis mattis leo. Suspendisse potenti. Nullam sit amet " +
                "orci fringilla, dictum lacus sed, luctus nisl. Integer porta lobortis lacus.\n" +
                "\n" +
                "Integer semper consequat sem, at convallis lacus aliquam nec. Nulla eget diam sed leo ultricies egestas vitae sit amet diam. " +
                "Fusce tempus a ex a pretium. Morbi sodales ligula id semper gravida. Curabitur pharetra ex elementum vestibulum pharetra. Maecenas" +
                " aliquam diam turpis, quis aliquet urna auctor vel. Maecenas egestas, tellus sed auctor feugiat, ex mi convallis nibh, at vulputate " +
                "turpis est vitae sem. Phasellus ut tellus eget sem consequat rhoncus. Donec ullamcorper magna eget scelerisque finibus. Nunc iaculis " +
                "hendrerit justo, semper vulputate ex suscipit sed. Phasellus dignissim egestas enim. In quis nisl tincidunt lorem pharetra bibendum blandit " +
                "gravida massa. Vestibulum et dolor mollis, blandit elit id, consectetur sapien.");
        course.setShortDescription("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Integer accumsan nisi nisl, in " +
                "eleifend felis fringilla ac. Donec ac egestas nunc.");
        course.addAuthor(user);
        course.setCreationDate(new Date());
        courseRepository.save(course);

    }

    private void initUsers() {
        Privilege admin = privilegeRepository.findByName("ROLE_ADMIN");
        Privilege courseWrite = privilegeRepository.findByName("COURSE_1_WRITE");
        final User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(encoder.encode("123"));
        user1.setEmail("admin@mail.ru");
        userRepository.save(user1);
        userRepository.addPermission(user1.getId(), admin.getId());
        userRepository.addPermission(user1.getId(), courseWrite.getId());

        final User user2 = new User();
        user2.setUsername("testUser");
        user2.setPassword(encoder.encode("123"));
        user2.setEmail("test@mail.ru");
        userRepository.save(user2);
    }



}