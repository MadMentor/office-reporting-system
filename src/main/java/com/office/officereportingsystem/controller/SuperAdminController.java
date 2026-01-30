package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;
import com.office.officereportingsystem.service.SuperAdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    @GetMapping("/dashboard")
    public String openDashboard(Model model) {

        return "super-admin/dashboard";
    }

    @GetMapping("/admin/create")
    public String openAdminFormPage(Model model) {
        if (!model.containsAttribute("adminCreateRequest")) {
            model.addAttribute("adminCreateRequest", new AdminCreateRequestDto());
        }
        return "super-admin/create-admin";
    }

    @PostMapping("/admin/save")
    public String createAdmin(
            @Valid @ModelAttribute("adminCreateRequest") AdminCreateRequestDto request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminCreateRequest", bindingResult);
            redirectAttributes.addFlashAttribute("adminCreateRequest", request);
            return "super-admin/create-admin";
        }

        // call service to create admin
        superAdminService.createAdmin(request);

        redirectAttributes.addFlashAttribute(
                "message",
                "ADMIN_CREATED_SUCCESS"
        );

        return "redirect:/super-admin/dashboard";
    }

    @GetMapping("/admin")
    public String openAdminListPage(Model model) {
        return "super-admin/list";
    }
}
