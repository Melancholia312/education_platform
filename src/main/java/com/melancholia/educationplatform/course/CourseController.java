package com.melancholia.educationplatform.course;


import com.melancholia.educationplatform.permissions.PrivilegeService;
import com.melancholia.educationplatform.review.Review;
import com.melancholia.educationplatform.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private ReviewRepository reviewRepository;


    @GetMapping("/courses")
    public List<Course> findCourses() {
        return courseRepository.findAll();
    }

    @PostAuthorize("hasPermission(#id, 'Review', 'read')")
    @GetMapping("/review/{id}")
    public Review findById(@PathVariable long id, Authentication authentication) {

        return reviewRepository.findById(id).orElse(null);
    }

    @PostFilter("hasPermission(filterObject.id, 'Review', 'read')")
    @GetMapping("/reviews")
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @GetMapping("/get")
    public void findById(Authentication authentication) {
        privilegeService.addPermissionToUser(authentication,"review", "read");
        privilegeService.addPermissionToUser(authentication,"review", "1", "read");
        //privilegeService.addPermissionToUser(authentication,"review", "2", "read");
    }
}
