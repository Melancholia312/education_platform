package com.melancholia.educationplatform.course.module;

import com.melancholia.educationplatform.course.Course;
import com.melancholia.educationplatform.course.CourseService;
import com.melancholia.educationplatform.user.permissions.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller("module")
@AllArgsConstructor
public class ModuleController {

    private final CourseService courseService;

    private final ModuleService  moduleService;

    private final PrivilegeService privilegeService;


    @GetMapping("/module/add")
    public String addModule(@RequestParam(name = "courseId") long courseId,
                            Model model){
        Module module = new Module();
        model.addAttribute("module", module);
        model.addAttribute("course", courseService.findCourseToConstructById(courseId));
        return "module/add";
    }

    @PostMapping("/module/add")
    public String createModule(@RequestParam(name = "courseId") long courseId,
                               @ModelAttribute
                               Module module){
        Course course = courseService.findCourseToConstructById(courseId);
        module.setCourse(course);
        if (module.getId() == 0) module.setModuleNumber(moduleService.maxModuleNumberCourseId(courseId)+1);

        moduleService.moduleSave(courseId, module);

        return String.format("redirect:/course/%s/constructor", courseId);
    }

    @GetMapping("/module/{id}/edit")
    public String editModule(@RequestParam(name = "courseId") long courseId,
                             @PathVariable("id") long id, Model model){
        model.addAttribute("module", moduleService.findModuleToConstructById(courseId, id));
        return "module/edit";
    }

    @GetMapping("/module/{id}/delete")
    public String deleteModuleForm(@RequestParam(name = "courseId") long courseId,
                                   @PathVariable("id") long id, Model model){
        model.addAttribute("module", moduleService.findModuleToConstructById(courseId, id));
        return "module/delete";
    }

    @PostMapping("/module/{id}/delete")
    public String deleteModule(@RequestParam(name = "courseId") long courseId,
                               @PathVariable("id") long id){
        moduleService.deleteModuleById(courseId, id);
        return String.format("redirect:/course/%s/constructor", courseId);
    }

    @PostMapping("/module/switch")
    public String switchModules(@RequestParam(name = "courseId") long courseId,
                                @ModelAttribute ModulesWrapper modules){
        for (Module module : modules.getModules()){
            moduleService.updateModuleNumber(courseId, module.getId(), module.getModuleNumber());
        }
        return String.format("redirect:/course/%s/constructor", courseId);
    }


}
