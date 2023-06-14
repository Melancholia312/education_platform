package com.melancholia.educationplatform.course;

import com.melancholia.educationplatform.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    String findCoursesOrderByStarsQuery = """
            SELECT ROUND(AVG(review.stars), 2) as avgStars, course.course_id, comments_allowed, creation_date, description, is_published, name, price, short_description
            FROM course
            LEFT JOIN review ON course.course_id = review.course_id
            WHERE is_published=true
            GROUP BY course.course_id
            ORDER BY avgStars""";

    String findCoursesOrderByUsersCountQuery = """
            SELECT count(course_users.user_id) as usersCount, course.course_id, comments_allowed, creation_date, description, is_published, name, price, short_description
                       FROM course
                       LEFT JOIN course_users ON course.course_id = course_users.course_id
                       WHERE is_published=true
                       GROUP BY course.course_id
                       ORDER BY usersCount""";

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET is_published = ?2 WHERE course_id = ?1", nativeQuery = true)
    int publishCourseById(long id, boolean publish);

    @Query(value = "SELECT *\n" +
            "\tFROM public.course WHERE is_published=true", nativeQuery = true)
    Page<Course> findAllPublishedCourses(Pageable pageable);

    @Query(value = findCoursesOrderByUsersCountQuery + " ASC", nativeQuery = true)
    Page<Course> findCoursesOrderByUsersCountAsc(Pageable pageable);

    @Query(value = findCoursesOrderByUsersCountQuery + " DESC", nativeQuery = true)
    Page<Course> findCoursesOrderByUsersCountDesc(Pageable pageable);

    @Query(value = findCoursesOrderByStarsQuery + " ASC", nativeQuery = true)
    Page<Course> findCoursesOrderByStarsAsc(Pageable pageable);

    @Query(value = findCoursesOrderByStarsQuery + " DESC", nativeQuery = true)
    Page<Course> findCoursesOrderByStarsDesc(Pageable pageable);

    Optional<Course> findByIdAndIsPublishedTrue(long id);

    @Modifying
    @Query(value = "insert into course_users (user_id, course_id) VALUES (:userId, :courseId)", nativeQuery = true)
    @Transactional
    void enrollUser(@Param("userId") long userId, @Param("courseId") long courseId);

    @Query(value ="select (count(*) > 0) from course_users where course_id = ?1 and user_id = ?2", nativeQuery = true)
    boolean isMember(long courseId, long userId);

    @Query(value = "SELECT * FROM Course WHERE (name LIKE %?1%"
            + " OR short_description LIKE %?1%"
            + " OR description LIKE %?1%) "
            + " AND is_published=true", nativeQuery = true)
    Page<Course> search(Pageable pageable, String keyword);

    /*@Query(value = "SELECT user_id, phone, password, email, username\n" +
            "FROM public.user\n" +
            "WHERE user_id=?1", nativeQuery = true)
    List<User> findAuthorsByCourse(long course_id);*/
/*

    @Query("select course_id, comments_allowed, creation_date, description, is_published, id, price from courses")
    List<Course> findCoursesWithUsersPermission();*/
}
