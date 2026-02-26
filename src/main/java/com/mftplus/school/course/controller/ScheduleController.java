package com.mftplus.school.course.controller;

import com.mftplus.school.core.dto.TeacherUpdateDto;
import com.mftplus.school.core.service.TeacherService;
import com.mftplus.school.course.dto.ScheduleCreateDto;
import com.mftplus.school.course.dto.ScheduleUpdateDto;


import com.mftplus.school.course.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    private final TeacherService teacherService;
    private final ScheduleService scheduleService;

    public ScheduleController(TeacherService teacherService,
                              ScheduleService scheduleService) {
        this.teacherService = teacherService;
        this.scheduleService = scheduleService;
    }

    // صفحه لیست زمان‌بندی‌ها
    @GetMapping
    public String listSchedules(Model model) {
        List<ScheduleUpdateDto> schedules = scheduleService.findAll();
        model.addAttribute("schedules", schedules);
        model.addAttribute("content", "schedule/list");
        return "layout";
    }

    // نمایش فرم ثبت زمان‌بندی
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("scheduleCreateDto", new ScheduleCreateDto());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("content", "schedule/create");
        return "layout";
    }

    // ثبت زمان‌بندی جدید
    @PostMapping("/create")
    public String createSchedule(@Valid @ModelAttribute("scheduleCreateDto") ScheduleCreateDto dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teachers", teacherService.findAll());
            model.addAttribute("content", "schedule/create");
            return "layout";
        }

        TeacherUpdateDto teacher = teacherService.findById(dto.getTeacherId());


        scheduleService.create(dto);

        return "redirect:/schedules";
    }
}
