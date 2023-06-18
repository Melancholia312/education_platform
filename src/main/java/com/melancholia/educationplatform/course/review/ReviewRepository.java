package com.melancholia.educationplatform.course.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    long deleteByAuthorAndCourseId(String author, long course_id);
}
