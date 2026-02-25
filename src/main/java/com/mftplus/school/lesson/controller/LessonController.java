package com.mftplus.school.lesson.controller;

import com.mftplus.school.lesson.dto.LessonDto;
import com.mftplus.school.lesson.model.Lesson;
import com.mftplus.school.lesson.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    // -----------------------------

    @GetMapping
    public String listLessons(Model model) {
        List<Lesson> lessons = lessonService.findAll();
        model.addAttribute("lessons", lessons);
        return "lessons";
    }
    // -----------------------------
    @GetMapping("/creat")
    public  String showCreateLesson(Model model) {
        model.addAttribute("lessonDto", new LessonDto());
        return "lessons/create";
    }
    // -----------------------------
    @PostMapping("/save")
    public  String saveLesson(@Valid @ModelAttribute("lesson") LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lessons/create";
        }
        lessonService.save(lessonDto);
        return "redirect:/lessons";
    }
    // -----------------------------
    @GetMapping("/edit/{id}")
    public String EditLesson(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", lessonService.findById(id));
        return "lessons/edit";
    }
    // -----------------------------
    @GetMapping("/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonService.DeleteById(id);
        return "redirect:/lessons";
    }



}
