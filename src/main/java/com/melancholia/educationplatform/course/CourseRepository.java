package com.melancholia.educationplatform.course;

import com.melancholia.educationplatform.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByAuthorsContains(User user);

/*
    @Query("select course_id, comments_allowed, creation_date, description, is_published, id, price from courses")
    List<Course> findCoursesWithUsersPermission();*/
}
