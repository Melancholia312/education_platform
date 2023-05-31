package com.melancholia.educationplatform.course.step;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseService;
import com.melancholia.educationplatform.course.module.Module;
import com.melancholia.educationplatform.course.module.ModuleService;
import com.melancholia.educationplatform.course.module.ModulesWrapper;
import com.melancholia.educationplatform.user.permissions.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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

    private final PrivilegeService privilegeService;



    @GetMapping("/step/add")
    public String addModule(@RequestParam(name = "moduleId") long moduleId,
                            @RequestParam(name = "type") String type,
                            Model model) {
        model.addAttribute("module", moduleService.findModuleToConstructById(moduleId));
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
    public String createLectionStep(@RequestParam(name = "moduleId") long moduleId,
                                    @ModelAttribute InformationTextStep step,
                                    Authentication authentication) {
        Module module = moduleService.findModuleToConstructById(moduleId);
        step.setModule(module);

        if (step.getId() == 0) step.setStepNumber(stepService.maxStepNumberModuleId(moduleId) + 1);

        stepService.stepSave(step);
        privilegeService.addPermissionToUser(
                authentication,
                Step.class.getSimpleName(),
                String.valueOf(step.getId()),
                "read");
        privilegeService.addPermissionToUser(
                authentication,
                Step.class.getSimpleName(),
                String.valueOf(step.getId()),
                "write");
        return String.format("redirect:/module/%s/steps", step.getModule().getId());
    }

    @PostMapping("/step/add-word-answer")
    public String createWordAnswerStep(@RequestParam(name = "moduleId") long moduleId,
                                       @ModelAttribute WordAnswerStep step,
                                       Authentication authentication) {
        Module module = moduleService.findModuleToConstructById(moduleId);
        step.setModule(module);

        if (step.getId() == 0) step.setStepNumber(stepService.maxStepNumberModuleId(moduleId) + 1);

        stepService.stepSave(step);
        privilegeService.addPermissionToUser(
                authentication,
                Step.class.getSimpleName(),
                String.valueOf(step.getId()),
                "read");
        privilegeService.addPermissionToUser(
                authentication,
                Step.class.getSimpleName(),
                String.valueOf(step.getId()),
                "write");
        return String.format("redirect:/module/%s/steps", step.getModule().getId());
    }

    @PostMapping("/step/add-test")
    public String createTestStep(@RequestParam(name = "moduleId") long moduleId,
                                 @ModelAttribute TestStep step,
                                 Authentication authentication) {
        Module module = moduleService.findModuleToConstructById(moduleId);
        step.setModule(module);

        if (step.getId() == 0) step.setStepNumber(stepService.maxStepNumberModuleId(moduleId) + 1);

        stepService.stepSave(step);
        privilegeService.addPermissionToUser(
                authentication,
                Step.class.getSimpleName(),
                String.valueOf(step.getId()),
                "read");
        privilegeService.addPermissionToUser(
                authentication,
                Step.class.getSimpleName(),
                String.valueOf(step.getId()),
                "write");
        return String.format("redirect:/module/%s/steps", step.getModule().getId());
    }

    @GetMapping("/module/{id}/steps")
    public String viewMySteps(@PathVariable("id") long id,
                              Model model) {
        model.addAttribute("module", moduleService.findModuleToConstructById(id));
        StepsWrapper stepsWrapper = new StepsWrapper(stepService.findStepsByModuleId(id));
        model.addAttribute("steps", stepsWrapper);
        return "step/all-steps";
    }

    @GetMapping("/step/{id}/edit")
    public String editStep(@PathVariable("id") long id, Model model,
                           @RequestParam(name = "type") String type) {
        model.addAttribute("step", stepService.findStepToConstructById(id));
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
    public String deleteStepForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("step", stepService.findStepToConstructById(id));
        return "step/delete";
    }

    @PostMapping("/step/{id}/delete")
    public String deleteStep( @RequestParam(name = "moduleId") long moduleId,
                             @PathVariable("id") long id) {

        stepService.deleteStepById(id);
        return String.format("redirect:/module/%s/steps", moduleId);
    }

    @PostMapping("/steps/switch")
    public String switchSteps(@RequestParam(name = "moduleId") long moduleId,
                              @ModelAttribute StepsWrapper steps) {
        for (Step step : steps.getSteps()) {
            stepService.updateStepNumber(step.getId(), step.getStepNumber());
        }
        return String.format("redirect:/module/%s/steps", moduleId);
    }

    @GetMapping("/test-step/{id}/answers")
    public String getTestStepAnswers(@PathVariable("id") long id,
                                     Model model) {

        model.addAttribute("step", stepService.findStepToConstructById(id));
        model.addAttribute("answers", answerService.findAnswerByTestStepId(id));
        return "step/all-answers";
    }

    @GetMapping("/test-step/answer/add")
    public String addTestStepAnswer(@RequestParam(name = "testStepId") long testStepId,
                                    Model model) {

        model.addAttribute("step", stepService.findStepToConstructById(testStepId));
        model.addAttribute("answer", new Answer());
        return "step/add-answer";
    }


    @PostMapping("/test-step/answer/add")
    public String createTestStepAnswer(@RequestParam(name = "testStepId") long stepId,
                                       @ModelAttribute Answer answer,
                                       Authentication authentication) {
        Step testStep = stepService.findStepToConstructById(stepId);
        answer.setTestStep(testStep);
        answerService.answerSave(answer);
        privilegeService.addPermissionToUser(
                authentication,
                Answer.class.getSimpleName(),
                String.valueOf(answer.getId()),
                "read");
        privilegeService.addPermissionToUser(
                authentication,
                Answer.class.getSimpleName(),
                String.valueOf(answer.getId()),
                "write");
        return String.format("redirect:/test-step/%s/answers", stepId);
    }

    @GetMapping("test-step/answer/edit")
    public String editModule(@RequestParam(name = "answerId") long answerId,
                             @RequestParam(name = "stepId") long stepId,
                             Model model) {

        model.addAttribute("step", stepService.findStepToConstructById(stepId));
        model.addAttribute("answer", answerService.findAnswerToConstructById(answerId));
        return "step/edit-answer";
    }

    @GetMapping("test-step/answer/delete")
    public String deleteAnswerForm(@RequestParam(name = "answerId") long answerId,
                                   @RequestParam(name = "stepId") long stepId,
                                   Model model) {

        model.addAttribute("step", stepService.findStepToConstructById(stepId));
        model.addAttribute("answer", answerService.findAnswerToConstructById(answerId));
        return "step/delete-answer";
    }

    @PostMapping("test-step/answer/delete")
    public String deleteAnswer(@RequestParam(name = "answerId") long answerId,
                               @RequestParam(name = "stepId") long stepId) {

        answerService.deleteAnswerById(answerId);
        return String.format("redirect:/test-step/%s/answers", stepId);
    }

}
