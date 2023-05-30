package com.melancholia.educationplatform.course.step;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    public List<Answer> findAnswerByTestStepId(long testStepId);

}
