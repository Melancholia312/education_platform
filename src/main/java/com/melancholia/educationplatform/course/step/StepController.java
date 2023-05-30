package com.melancholia.educationplatform.course.step;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseService;
import com.melancholia.educationplatform.course.module.Module;
import com.melancholia.educationplatform.course.module.ModuleService;
import com.melancholia.educationplatform.course.module.ModulesWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller("/step")
@AllArgsConstructor
public class StepController {

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final StepService stepService;

    private final AnswerService answerService;

    private final StepRepository stepRepository;

    @GetMapping("/step/add")
    public String addModule(@RequestParam(name = "courseId") long courseId,
                            @RequestParam(name = "moduleId") long moduleId,
                            @RequestParam(name = "type") String type,
                            Model model) {
        model.addAttribute("module", moduleService.findModuleToConstructById(courseId, moduleId));
        Step step;
        switch (type) {
            case "word-answer-step" -> {
                step = new WordAnswerStep();
                model.addAttribute("step", step);
                return "step/add-word-answer-step";
            }
            case "test-step" -> {
                step = new TestStep();
                model.addAttribute("step", step);
                return "step/add-test-step";
            }
            default -> {
                step = new InformationTextStep();
                model.addAttribute("step", step);
                return "step/add-lection-step";
            }
        }
    }

    @PostMapping("/step/add-lection")
    public String createLectionStep(@RequestParam(name = "courseId") long courseId,
                                    @RequestParam(name = "moduleId") long moduleId,
                                    @ModelAttribute InformationTextStep step) {
        Module module = moduleService.findModuleToConstructById(courseId, moduleId);
        step.setModule(module);

        if (step.getId() == 0) step.setStepNumber(stepService.maxStepNumberModuleId(moduleId) + 1);

        stepService.stepSave(courseId, step);
        return String.format("redirect:/module/%s/steps?courseId=%s", step.getModule().getId(), courseId);
    }

    @PostMapping("/step/add-word-answer")
    public String createWordAnswerStep(@RequestParam(name = "courseId") long courseId,
                                       @RequestParam(name = "moduleId") long moduleId,
                                       @ModelAttribute WordAnswerStep step) {
        Module module = moduleService.findModuleToConstructById(courseId, moduleId);
        step.setModule(module);

        if (step.getId() == 0) step.setStepNumber(stepService.maxStepNumberModuleId(moduleId) + 1);

        stepService.stepSave(courseId, step);
        return String.format("redirect:/module/%s/steps?courseId=%s", step.getModule().getId(), courseId);
    }

    @PostMapping("/step/add-test")
    public String createTestStep(@RequestParam(name = "courseId") long courseId,
                                 @RequestParam(name = "moduleId") long moduleId,
                                 @ModelAttribute TestStep step) {
        Module module = moduleService.findModuleToConstructById(courseId, moduleId);
        step.setModule(module);

        if (step.getId() == 0) step.setStepNumber(stepService.maxStepNumberModuleId(moduleId) + 1);

        stepService.stepSave(courseId, step);
        return String.format("redirect:/module/%s/steps?courseId=%s", step.getModule().getId(), courseId);
    }

    @GetMapping("/module/{id}/steps")
    public String viewMySteps(@PathVariable("id") long id,
                              @RequestParam(name = "courseId") long courseId,
                              Model model) {
        StepsWrapper stepsWrapper = new StepsWrapper(stepService.findStepsByModuleId(courseId, id));
        model.addAttribute("steps", stepsWrapper);
        model.addAttribute("module", moduleService.findModuleToConstructById(courseId, id));
        return "step/all-steps";
    }

    @GetMapping("/step/{id}/edit")
    public String editStep(@RequestParam(name = "courseId") long courseId,
                           @PathVariable("id") long id, Model model,
                           @RequestParam(name = "type") String type) {
        model.addAttribute("step", stepService.findStepToConstructById(courseId, id));
        switch (type) {
            case "WordAnswerStep" -> {
                return "step/edit-word-answer-step";
            }
            case "TestStep" -> {
                return "step/edit-test-step";
            }
            default -> {
                return "step/edit-lection-step";
            }
        }

    }

    @GetMapping("/step/{id}/delete")
    public String deleteStepForm(@RequestParam(name = "courseId") long courseId,
                                 @PathVariable("id") long id, Model model) {
        model.addAttribute("step", stepService.findStepToConstructById(courseId, id));
        return "step/delete";
    }

    @PostMapping("/step/{id}/delete")
    public String deleteStep(@RequestParam(name = "courseId") long courseId,
                             @RequestParam(name = "moduleId") long moduleId,
                             @PathVariable("id") long id) {

        stepService.deleteStepById(courseId, id);
        return String.format("redirect:/module/%s/steps?courseId=%s", moduleId, courseId);
    }

    @PostMapping("/steps/switch")
    public String switchSteps(@RequestParam(name = "courseId") long courseId,
                              @RequestParam(name = "moduleId") long moduleId,
                              @ModelAttribute StepsWrapper steps) {
        for (Step step : steps.getSteps()) {
            stepService.updateStepNumber(courseId, step.getId(), step.getStepNumber());
        }
        return String.format("redirect:/module/%s/steps?courseId=%s", moduleId, courseId);
    }

    @GetMapping("/test-step/{id}/answers")
    public String getTestStepAnswers(@RequestParam(name = "courseId") long courseId,
                                     @PathVariable("id") long id,
                                     Model model) {
        model.addAttribute("answers", answerService.findAnswerByTestStepId(courseId, id));
        model.addAttribute("step", stepService.findStepToConstructById(courseId, id));
        return "step/all-answers";
    }

    @GetMapping("/test-step/answer/add")
    public String addTestStepAnswer(@RequestParam(name = "testStepId") long testStepId,
                                    @RequestParam(name = "courseId") long courseId,
                                    Model model) {
        model.addAttribute("answer", new Answer());
        model.addAttribute("step", stepService.findStepToConstructById(courseId, testStepId));
        return "step/add-answer";
    }


    @PostMapping("/test-step/answer/add")
    public String createTestStepAnswer(@RequestParam(name = "courseId") long courseId,
                                       @RequestParam(name = "testStepId") long stepId,
                                       @ModelAttribute Answer answer) {
        Step testStep = stepService.findStepToConstructById(courseId, stepId);
        answer.setTestStep(testStep);
        answerService.answerSave(courseId, answer);
        return String.format("redirect:/test-step/%s/answers?courseId=%s", stepId, testStep.getModule().getCourse().getId());
    }

    @GetMapping("test-step/answer/edit")
    public String editModule(@RequestParam(name = "courseId") long courseId,
                             @RequestParam(name = "answerId") long answerId,
                             @RequestParam(name = "stepId") long stepId,
                             Model model) {
        model.addAttribute("answer", answerService.findAnswerToConstructById(courseId, answerId));
        model.addAttribute("step", stepService.findStepToConstructById(courseId, stepId));
        return "step/edit-answer";
    }

    @GetMapping("test-step/answer/delete")
    public String deleteAnswerForm(@RequestParam(name = "courseId") long courseId,
                                   @RequestParam(name = "answerId") long answerId,
                                   @RequestParam(name = "stepId") long stepId,
                                   Model model) {
        model.addAttribute("answer", answerService.findAnswerToConstructById(courseId, answerId));
        model.addAttribute("step", stepService.findStepToConstructById(courseId, stepId));
        return "step/delete-answer";
    }

    @PostMapping("test-step/answer/delete")
    public String deleteAnswer(@RequestParam(name = "courseId") long courseId,
                               @RequestParam(name = "answerId") long answerId,
                               @RequestParam(name = "stepId") long stepId) {

        answerService.deleteAnswerById(courseId, answerId);
        return String.format("redirect:/test-step/%s/answers?courseId=%s", stepId, courseId);
    }

}
