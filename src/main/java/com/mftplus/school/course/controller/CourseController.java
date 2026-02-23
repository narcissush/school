package com.mftplus.school.course.controller;

import com.mftplus.school.core.model.Teacher;
import com.mftplus.school.course.dto.CourseCreateDto;
import com.mftplus.school.course.dto.CourseUpdateDto;
import com.mftplus.school.course.dto.ScheduleUpdateDto;
import com.mftplus.school.course.service.CourseService;
import com.mftplus.school.course.service.ScheduleService;
import com.mftplus.school.core.repository.TeacherRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ScheduleService scheduleService;
    private final TeacherRepository teacherRepository;

    // ---------------- لیست دوره‌ها ----------------
    @GetMapping
    public String listCourses(Model model) {
        List<CourseUpdateDto> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "course/list"; // templates/course/list.html
    }

    // ---------------- فرم ایجاد دوره ----------------
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("courseCreateDto", new CourseCreateDto());

        List<Teacher> teachers = teacherRepository.findAll();
        model.addAttribute("teachers", teachers);

        // لیست Scheduleهای آزاد ابتدا خالی است، بعد با انتخاب استاد پر می‌شود
        model.addAttribute("freeSchedules", List.of());

        return "course/create";
    }

    // ---------------- ذخیره دوره جدید ----------------
    @PostMapping
    public String createCourse(@Valid @ModelAttribute CourseCreateDto courseCreateDto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "course/create";
        }
        courseService.create(courseCreateDto);
        redirectAttributes.addFlashAttribute("success", "دوره با موفقیت ایجاد شد");
        return "redirect:/courses";
    }

    // ---------------- فرم ویرایش دوره ----------------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        CourseUpdateDto course = courseService.findById(id);
        model.addAttribute("courseUpdateDto", course);

        List<Teacher> teachers = teacherRepository.findAll();
        model.addAttribute("teachers", teachers);

        // اگر استاد انتخاب شده باشد، لیست Scheduleهای آزاد آن استاد
        if (course.getTeacherId() != null) {
            List<ScheduleUpdateDto> freeSchedules = scheduleService.findFreeSchedulesByTeacher(course.getTeacherId());
            model.addAttribute("freeSchedules", freeSchedules);
        } else {
            model.addAttribute("freeSchedules", List.of());
        }

        return "course/edit";
    }

    // ---------------- بروزرسانی دوره ----------------
    @PostMapping("/{id}/edit")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute CourseUpdateDto courseUpdateDto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "course/edit";
        }
        courseService.update(id, courseUpdateDto);
        redirectAttributes.addFlashAttribute("success", "دوره با موفقیت بروزرسانی شد");
        return "redirect:/courses";
    }

    // ---------------- حذف دوره ----------------
    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        courseService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "دوره با موفقیت حذف شد");
        return "redirect:/courses";
    }

    // ---------------- جزئیات دوره ----------------
    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        CourseUpdateDto course = courseService.findById(id);
        model.addAttribute("course", course);
        return "course/view";
    }

    // ---------------- AJAX: لیست Scheduleهای آزاد استاد ----------------
    @GetMapping("/free-schedules/{teacherId}")
    @ResponseBody
    public List<ScheduleUpdateDto> getFreeSchedules(@PathVariable Long teacherId) {
        return scheduleService.findFreeSchedulesByTeacher(teacherId);
    }
}