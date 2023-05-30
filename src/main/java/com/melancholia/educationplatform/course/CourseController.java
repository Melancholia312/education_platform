package com.melancholia.educationplatform.course;


import com.melancholia.educationplatform.course.module.ModuleService;
import com.melancholia.educationplatform.course.module.ModulesWrapper;
import com.melancholia.educationplatform.user.User;
import com.melancholia.educationplatform.user.permissions.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final PrivilegeService privilegeService;

    private final ModuleService  moduleService;

    @GetMapping("/")
    public String mainPageWithCourses(){
        return "course/index";
    }

    @GetMapping("/course/add")
    @PreAuthorize("isAuthenticated()")
    public String createCourse(Model model){
        model.addAttribute("course", new Course());
        return "course/add";
    }

    @GetMapping("/course/{id}/edit")
    public String editCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("course", courseService.findCourseToConstructById(id));
        return "course/add";
    }

    @PostMapping("/course/add")
    @PreAuthorize("isAuthenticated()")
    public String createCourse(@ModelAttribute Course course, Authentication authentication){
        course.setCreationDate(new Date());
        course.addAuthor(( (User) authentication.getPrincipal() ));
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

    @GetMapping("/course/{id}/delete")
    public String deleteCourseForm(@PathVariable("id") long id, Model model){
        model.addAttribute("course",  courseService.findCourseToConstructById(id));
        return "course/delete";
    }

    @PostMapping("/course/{id}/delete")
    public String deleteCourse(@PathVariable("id") long id){
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

    @GetMapping("/course/{id}/promo")
    public String getPromoCourseById(@PathVariable("id") long id,
                                Authentication authentication,
                                Model model) {
        model.addAttribute("course", courseService.findCourseById(id));
        return "course/course-promo";
    }

    @GetMapping("/course/{id}")
    public String getCourseById(@PathVariable("id") long id,
                                     Authentication authentication,
                                     Model model) {
        model.addAttribute("course", courseService.findCourseToLearnById(id));
        return "course/course";
    }

    @PostMapping("/course/{id}/enroll")
    public String enrollToCourseById(@PathVariable("id") long id,
                                     Authentication authentication) {
        return "redirect to somewhere (course/course)";
    }

}
