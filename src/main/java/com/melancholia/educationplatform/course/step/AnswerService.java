package com.melancholia.educationplatform.course.step;

import com.melancholia.educationplatform.core.exception.AnswerNotFoundException;
import com.melancholia.educationplatform.core.exception.ModuleNotFoundException;
import com.melancholia.educationplatform.course.module.Module;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public List<Answer> findAnswerByTestStepId(long courseId, long testStepId){
        return answerRepository.findAnswerByTestStepId(testStepId);
    }

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public Answer findAnswerToConstructById(long courseId, long answerId){
        return findAnswerById(answerId);
    }

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public void deleteAnswerById(long courseId, long answerId){
        answerRepository.deleteById(answerId);
    }

    public Answer findAnswerById(long id){
        return answerRepository.findById(id).orElseThrow(
                () -> new AnswerNotFoundException(String.format("Answer with ID %s not found", id))
        );
    }

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public void answerSave(long courseId, Answer answer){
        answerRepository.save(answer);
    }
}
