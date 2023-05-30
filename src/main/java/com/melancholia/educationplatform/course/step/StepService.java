package com.melancholia.educationplatform.course.step;

import com.melancholia.educationplatform.core.exception.ModuleNotFoundException;
import com.melancholia.educationplatform.core.exception.StepNotFoundException;
import com.melancholia.educationplatform.course.module.Module;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StepService {

    private final StepRepository stepRepository;


    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public void stepSave(long courseId, Step step){
        stepRepository.save(step);
    }

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public Step findStepToConstructById(long courseId, long id){
        return findStepById(id);
    }

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public void deleteStepById(long courseId, long id){
        stepRepository.deleteById(id);
    }


    public Step findStepById(long id){
        return stepRepository.findById(id).orElseThrow(
                () -> new StepNotFoundException(String.format("Step with ID %s not found", id))
        );
    }

    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public void updateStepNumber(long courseId, long stepId, long stepNumber){
        stepRepository.updateStepNumber(stepNumber, stepId);
    }
    @PreAuthorize("hasPermission(#courseId, 'Course', 'write')")
    public List<Step> findStepsByModuleId(long courseId, long moduleId){
        return stepRepository.findStepsByModuleIdOrderByStepNumber(moduleId);
    }

    public long maxStepNumberModuleId(long moduleId){
        Long value = stepRepository.maxStepNumberModuleId(moduleId);
        if (value == null) return 0;
        return value;
    }

}
