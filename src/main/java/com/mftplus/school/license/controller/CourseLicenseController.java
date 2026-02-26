package com.mftplus.school.license.controller;

import com.mftplus.school.license.dto.CourseLicenseDto;
import com.mftplus.school.license.service.CourseLicenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class CourseLicenseController {

    private final CourseLicenseService courseLicenseService;

    private void prepareListModel(Model model, int page, int size, Long studentId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<CourseLicenseDto> licensePage;

        if (studentId != null) {
            licensePage = courseLicenseService.findByStudentId(studentId, pageable);
        } else {
            licensePage = courseLicenseService.findAll(pageable);
        }

        model.addAttribute("licenses", licensePage);
        model.addAttribute("studentId", studentId);
    }

    @GetMapping
    public String getAllLicenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long studentId,
            Model model
    ) {
        prepareListModel(model, page, size, studentId);

        model.addAttribute("isTrash", false);

        if (!model.containsAttribute("license")) {
            CourseLicenseDto dto = new CourseLicenseDto();
            dto.setStudentId(studentId); // prefill if coming from student page
            model.addAttribute("license", dto);
        }

        return "license/list";
    }

    @GetMapping("/trash")
    public String getTrash(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<CourseLicenseDto> deletedPage = courseLicenseService.findAllSoftDeleted(pageable);

        model.addAttribute("licenses", deletedPage);
        model.addAttribute("isTrash", true);

        if (!model.containsAttribute("license")) {
            model.addAttribute("license", new CourseLicenseDto());
        }

        return "license/list";
    }

    @PostMapping
    public String createLicense(
            @Valid @ModelAttribute("license") CourseLicenseDto licenseDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        if (licenseDto.getCertificateDate() != null
                && licenseDto.getExpiryDate() != null
                && licenseDto.getCertificateDate().isAfter(licenseDto.getExpiryDate())) {
            result.rejectValue("certificateDate", "invalidDateOrder",
                    "Certificate date cannot be after expiry date");
        }
        if (licenseDto.getLicenseNumber() != null
                && courseLicenseService.existsByLicenseNumber(licenseDto.getLicenseNumber())) {
            result.rejectValue("licenseNumber", "duplicate", "License number already exists");
        }

        if (result.hasErrors()) {
            prepareListModel(model, page, size, licenseDto.getStudentId());
            model.addAttribute("isTrash", false);
            model.addAttribute("showModal", true);
            model.addAttribute("formAction", "/licenses");
            return "license/list";
        }

        courseLicenseService.save(licenseDto);
        redirectAttributes.addFlashAttribute("successMessage", "Course license created successfully!");
        return (licenseDto.getStudentId() != null)
                ? "redirect:/licenses?studentId=" + licenseDto.getStudentId()
                : "redirect:/licenses";
    }

    @PutMapping("/{id}")
    public String updateLicense(
            @PathVariable Long id,
            @Valid @ModelAttribute("license") CourseLicenseDto licenseDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        if (licenseDto.getCertificateDate() != null
                && licenseDto.getExpiryDate() != null
                && licenseDto.getCertificateDate().isAfter(licenseDto.getExpiryDate())) {
            result.rejectValue("certificateDate", "invalidDateOrder",
                    "Certificate date cannot be after expiry date");
        }

        if (result.hasErrors()) {
            prepareListModel(model, page, size, licenseDto.getStudentId());
            model.addAttribute("isTrash", false);
            model.addAttribute("showModal", true);
            model.addAttribute("formAction", "/licenses/" + id);
            licenseDto.setId(id);
            return "license/list";
        }

        licenseDto.setId(id);
        courseLicenseService.update(licenseDto);

        redirectAttributes.addFlashAttribute("successMessage", "Course license updated successfully!");
        return (licenseDto.getStudentId() != null)
                ? "redirect:/licenses?studentId=" + licenseDto.getStudentId()
                : "redirect:/licenses";
    }

    @DeleteMapping("/{id}")
    public String deleteLicense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseLicenseService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Course license moved to trash.");
        return "redirect:/licenses";
    }

    @PostMapping("/restore/{id}")
    public String restoreLicense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseLicenseService.restoreSoftDeletedById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Course license restored successfully!");
        return "redirect:/licenses/trash";
    }
}