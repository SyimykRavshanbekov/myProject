package peaksoft.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import peaksoft.model.Course;
import peaksoft.model.Group;
import peaksoft.service.CompanyService;
import peaksoft.service.CourseService;
import peaksoft.service.GroupService;

import javax.validation.Valid;


@Controller
public class CourseController {
    private final CompanyService companyService;
    private final CourseService courseService;

    private final GroupService groupService;

    @Autowired
    public CourseController(CompanyService companyService, CourseService courseService, GroupService groupService) {
        this.companyService = companyService;
        this.courseService = courseService;
        this.groupService = groupService;
    }

    @GetMapping("/courses/{id}")
    public String getAllCourses(@PathVariable Long id, Model model,
                                @ModelAttribute("group") Group group) {
        model.addAttribute("courses", courseService.getAllCourses(id));
        model.addAttribute("groups", groupService.getAllGroup(id));
        model.addAttribute("companyId",id);
        return "/course/courses";
    }

    @GetMapping("/courses/{id}/addCourse")
    public String addCourse(@PathVariable Long id, Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("companyId", id);
        return "/course/addCourse";
    }

    @PostMapping("/{id}/saveCourse")
    public String saveCourse(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult,
                             @PathVariable Long id) {
        if (bindingResult.hasErrors()){
            return "redirect:/courses/"+id+"/addCourse";
        }
        courseService.addCourse(id, course);
        return "redirect:/courses/"+id;
    }

    @GetMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("companyId", course.getCompany().getId());
        return "/course/update_courses";
    }

    @GetMapping("/{companyId}/{id}/saveUpdateCourse")
    public String saveUpdateCourse(@PathVariable("companyId") Long companyId,
                                   @PathVariable("id") Long id,
                                   @ModelAttribute("course") Course course) {
        courseService.updateCourse(course,id);
        return "redirect:/courses/"+companyId;
    }

    @GetMapping("/{companyId}/{id}/deleteCourse")
    public String deleteCourse(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        courseService.deleteCourse(id);
        return "redirect:/courses/"+companyId;
    }

    @PostMapping("{courseId}/{companyId}/assignGroup")
    private String assignGroup(@PathVariable("companyId") Long comId,
                                    @PathVariable("courseId") Long courseId,
                                    @ModelAttribute("group") Group group) {
        groupService.assignGroup(courseId, group.getId());
        return "redirect:/courses/" + comId;
    }
}