package com.melancholia.educationplatform.course;

import com.melancholia.educationplatform.core.exception.CourseNotFoundException;
import com.melancholia.educationplatform.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    /*public List<Course> findByAuthorsContains(User user){
        return courseRepository.findByAuthorsContains(user);
    }*/

    public Course findCourseById(long id){
        return courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundException(String.format("Course with ID %s not found", id))
        );
    }

    @PreAuthorize("hasPermission(#id, 'Course', 'write')")
    public Course findCourseToConstructById(long id){
        return findCourseById(id);
    }

    @PreAuthorize("hasPermission(#id, 'Course', 'read')")
    public Course findCourseToLearnById(long id){
        return findCourseById(id);
    }

    @PreAuthorize("hasPermission(#id, 'Course', 'write')")
    public void deleteCourseById(long id){
        courseRepository.deleteById(id);
    }

    @PostFilter("hasPermission(filterObject.id, 'Course', 'write')")
    public List<Course> findCoursesWithUsersPermission(){
        return courseRepository.findAll();
    }

    public void courseSave(Course course){
        courseRepository.save(course);
    }
    /*
    public List<Course> findNewestCourses(){
        return courseRepository.findByOrderByCreationDateAsc();
    }*/
    /*
    public List<Course> findPopularCourses(){

    }*/
        /*
    @PreAuthorize("hasPermission(#id, 'Course', 'write')")
    public void editCourseById(long id){
        courseRepository.(id);
    }*/
}
