package com.melancholia.educationplatform.course;


import com.melancholia.educationplatform.course.module.Module;
import com.melancholia.educationplatform.course.module.ModuleService;
import com.melancholia.educationplatform.course.module.ModulesWrapper;
import com.melancholia.educationplatform.course.review.Review;
import com.melancholia.educationplatform.course.step.*;
import com.melancholia.educationplatform.course.step.comment.Comment;
import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.user.permissions.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final PrivilegeService privilegeService;

    private final ModuleService moduleService;

    private final CourseRepository courseRepository;

    private final SolutionService solutionService;

    @GetMapping("/")
    public String mainPageWithCourses() {
        return "redirect:/catalog/page/1";
    }

    @GetMapping("/course/add")
    @PreAuthorize("isAuthenticated()")
    public String createCourse(Model model) {
        model.addAttribute("course", new Course());
        return "course/add";
    }

    @GetMapping("/course/{id}/edit")
    public String editCourseForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseService.findCourseToConstructById(id));
        return "course/edit";
    }

    @PostMapping("/course/add")
    @PreAuthorize("isAuthenticated()")
    public String createCourse(@ModelAttribute Course course, Authentication authentication) {
        course.setCreationDate(new Date());
        course.addAuthor(((User) authentication.getPrincipal()));
        courseService.courseSave(course);

        privilegeService.addPermissionToUser(
                authentication,
                Course.class.getSimpleName(),
                String.valueOf(course.getId()),
                "read");
        privilegeService.addPermissionToUser(
                authentication,
                Course.class.getSimpleName(),
                String.valueOf(course.getId()),
                "write");

        return "redirect:/my-courses";
    }

    @PostMapping("/course/edit")
    @PreAuthorize("isAuthenticated()")
    public String editCourse(@ModelAttribute Course course) {
        course.setCreationDate(new Date());
        courseService.courseSave(course);
        return "redirect:/my-courses";
    }

    @GetMapping("/course/{id}/delete")
    public String deleteCourseForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseService.findCourseToConstructById(id));
        return "course/delete";
    }

    @PostMapping("/course/{id}/delete")
    public String deleteCourse(@PathVariable("id") long id) {
        courseService.deleteCourseById(id);
        return "redirect:/my-courses";
    }

    @GetMapping("/my-courses")
    @PreAuthorize("isAuthenticated()")
    public String getCreatedCourses(Authentication authentication,
                                    Model model) {
        model.addAttribute("courses", courseService.findCoursesWithUsersPermission());
        return "course/my-courses";
    }

    @GetMapping("/course/{id}/constructor")
    public String getConstructorCourseById(@PathVariable("id") long id,
                                           Authentication authentication,
                                           Model model) {
        model.addAttribute("course", courseService.findCourseToConstructById(id));
        ModulesWrapper modulesWrapper = new ModulesWrapper(moduleService.getCourseModules(id));
        model.addAttribute("modules", modulesWrapper);
        return "course/course-constructor";
    }

    @PostMapping("/course/{id}/publish")
    public String publishCourse(@PathVariable("id") long id) {
        courseService.publishCourse(id, true);
        return "redirect:/my-courses";
    }

    @PostMapping("/course/{id}/close")
    public String closeCourse(@PathVariable("id") long id) {
        courseService.publishCourse(id, false);
        return "redirect:/my-courses";
    }

    @GetMapping("/course/{id}/promo")
    public String getPromoCourseById(@PathVariable("id") long id,
                                     Model model,
                                     Authentication authentication) {
        boolean isMember;
        if (authentication == null) {
            isMember = false;
        } else {
            isMember = courseService.isMember(((User) authentication.getPrincipal()).getId(), id);
        }
        Course course = courseService.findPublishedCourseById(id);
        course.sortModules();
        Module firstModule = course.getModules().get(0);
        firstModule.sortSteps();

        model.addAttribute("course", course);
        model.addAttribute("isMember", isMember);
        model.addAttribute("review", new Review());
        model.addAttribute("firstModuleId", firstModule.getId());
        model.addAttribute("firstStepId", firstModule.getSteps().get(0).getId());
        return "course/promo";

    }

    @GetMapping("/course/{id}")
    public String getCourseById(@PathVariable("id") long id,
                                Authentication authentication,
                                Model model) {
        model.addAttribute("course", courseService.findCourseToLearnById(id));
        return "course/course";
    }

    @PostMapping("/course/{id}/enroll")
    @PreAuthorize("isAuthenticated()")
    public String enrollToCourseById(@PathVariable("id") long id,
                                     Authentication authentication) {
        privilegeService.addPermissionToUser(
                authentication,
                Course.class.getSimpleName(),
                String.valueOf(id),
                "read");
        courseService.enrollUser(((User) authentication.getPrincipal()).getId(), id);
        return String.format("redirect:/course/%s/promo", id);
    }

    @GetMapping("/catalog/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", required = false, defaultValue = "date") String sortField,
                                @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
                                Model model) {
        int pageSize = 2;

        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCourses", page.getContent());
        return "course/catalog";
    }

    @GetMapping("/catalog/search/page/{pageNo}")
    public String search(@PathVariable(value = "pageNo") int pageNo,
                         @RequestParam(value = "keyword") String keyword,
                         Model model) {
        int pageSize = 2;

        Page<Course> page = courseService.search(pageNo, pageSize, keyword);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("keyword", keyword);
        model.addAttribute("listCourses", page.getContent());
        return "course/catalog";
    }

    @GetMapping("/course/{id}/passing")
    public String coursePassing(@PathVariable(value = "id") int id,
                                @RequestParam(value = "moduleId") String moduleId,
                                @RequestParam(value = "stepId") String stepId,
                                Model model,
                                Authentication authentication) {
        Course course = courseService.findCourseToLearnById(id);
        course.sortModules();
        Module module = course.getModuleById(Long.parseLong(moduleId));
        List<Solution> solutions = solutionService.getUserSolutions(
                ((User) authentication.getPrincipal()).getId(),
                Long.parseLong(stepId));
        Comparator<Solution> compareByDate = Comparator
                .comparing(Solution::getSolutionDate).reversed();
        boolean isPassed = false;
        if (solutions.stream().filter(Solution::isSolvedCorrect).findAny().orElse(null) != null) {
            isPassed = true;
        }
        for (Module sortModule : course.getModules()) {
            sortModule.sortSteps();
        }
        Step step = module.getStepById(Long.parseLong(stepId));
        Step nextStep = module.getNextStep(step);
        Module nextModule = module;
        if (nextStep == null) {
            nextModule = course.getNextModule(module);
            if (nextModule != null) {
                nextStep = nextModule.getSteps().get(0);
            }
        }

        model.addAttribute("course", course);
        model.addAttribute("module", module);
        model.addAttribute("step", step);
        model.addAttribute("nextModule", nextModule);
        model.addAttribute("nextStep", nextStep);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("isPassed", isPassed);
        model.addAttribute("userSolutions", solutions.stream().sorted(compareByDate).collect(Collectors.toList()));
        return "course/passing";
    }

    @GetMapping("/my-learning")
    @PreAuthorize("isAuthenticated()")
    public String getLearningCourses(Authentication authentication,
                                     Model model) {
        List<Course> courses = courseService.findCoursesWithUserReadPermission();
        List<Long> courseIds = courses.stream().map(Course::getId).toList();
        model.addAttribute("courses", courses);
        model.addAttribute("userProgressMap",
                courseService.checkUserProgress(((User) authentication.getPrincipal()).getId(), courseIds));
        return "course/my-learning";
    }

    @GetMapping("/course/{id}/unsubscribe")
    @PreAuthorize("isAuthenticated()")
    public String unsubscribe(@PathVariable(value = "id") int courseId,
                              Authentication authentication) {
        courseService.unsubscribe(((User) authentication.getPrincipal()).getId(), courseId);
        return "course/my-learning";
    }
}
