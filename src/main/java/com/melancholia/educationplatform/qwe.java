package com.melancholia.educationplatform;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseRepository;
import com.melancholia.educationplatform.course.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class qwe {

    private final CourseService courseService;

    @GetMapping("/test/{id}")
    public Course qwwee(@PathVariable("id") long id){
        return courseService.findCourseById(id);
    }

}
