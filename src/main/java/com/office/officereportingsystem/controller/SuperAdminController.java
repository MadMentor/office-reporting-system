package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.AdminCreateRequestDto;
import com.office.officereportingsystem.dto.request.AdminUpdateRequestDto;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.service.SuperAdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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
        model.addAttribute("admins", superAdminService.getAllAdmins());
        return "super-admin/list-admins";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteAdmin(
            @PathVariable("id") Integer adminId,
            RedirectAttributes redirectAttributes
    ) {
        superAdminService.deleteAdmin(adminId);

        redirectAttributes.addFlashAttribute("message", "ADMIN_DELETED_SUCCESS");

        return "redirect:/super-admin/admin"; // redirect to admin list page
    }

    @GetMapping("/admin/edit/{id}")
    public String editAdmin(@PathVariable Integer id, Model model) throws IOException {
        model.addAttribute("adminUpdateRequest", superAdminService.getAdminById(id));
        model.addAttribute("adminId", id);
        return "super-admin/update-admin";
    }

    @PostMapping("/admin/update/{id}")
    public String updateAdmin(
            @PathVariable("id") Integer adminId,
            @ModelAttribute("adminUpdateRequest") @Valid AdminUpdateRequestDto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("isUpdate", true);
            return "super-admin/update-admin";
        }

        try {
            superAdminService.updateAdmin(adminId, dto);
            redirectAttributes.addFlashAttribute("message", "ADMIN_UPDATED_SUCCESS");
            return "redirect:/super-admin/admin";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("isUpdate", true);
            model.addAttribute("adminUpdateRequest", e.getAdminData());
            model.addAttribute("error", e.getMessage());
            return "super-admin/update-admin";
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/super-admin/admin";
        }
    }

}
