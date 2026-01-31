package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.AccountCreateRequestDto;
import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.service.AccountService;
import com.office.officereportingsystem.service.SuperAdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;
    private final AccountService accountService;

    public SuperAdminController(SuperAdminService superAdminService, AccountService accountService) {
        this.superAdminService = superAdminService;
        this.accountService = accountService;
    }

//    @GetMapping("/dashboard")
//    public String openDashboard(Model model) {
//        return "super-admin/dashboard";
//    }

    @GetMapping("/admin/create")
    public String openAdminFormPage(Model model) {
        if (!model.containsAttribute("adminCreateRequest")) {
            model.addAttribute("adminCreateRequest", new AccountCreateRequestDto());
        }

        return "super-admin/create-admin";
    }

    @PostMapping("/admin/save")
    public String createAdmin(
            @Valid @ModelAttribute("adminCreateRequest") AccountCreateRequestDto request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.adminCreateRequest",
                    result
            );
            redirectAttributes.addFlashAttribute("adminCreateRequest", request);
            return "redirect:/super-admin/admin/create";
        }

        try {
            accountService.createAdmin(request);
            redirectAttributes.addFlashAttribute("message", "ADMIN_CREATED_SUCCESS");
            return "redirect:/dashboard";
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("adminCreateRequest", request);
            return "redirect:/super-admin/admin/create";
        }
    }

    @GetMapping("/admin")
    public String openAdminListPage(Model model) {
        model.addAttribute("admins", superAdminService.getAllAdmins());
        return "super-admin/list-admins";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteAdmin(
            @PathVariable("id") Integer adminId,
            RedirectAttributes redirectAttributes,
            Principal principal
    ) {
        try {
            accountService.deleteAccount(adminId, principal.getName());
            redirectAttributes.addFlashAttribute("message", "ADMIN_DELETED_SUCCESS");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/super-admin/admin";
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
            @ModelAttribute("adminUpdateRequest") @Valid AccountUpdateRequestDto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("adminId", adminId);
            return "super-admin/update-admin";
        }

        try {
            accountService.updateAccount(adminId, dto);
            redirectAttributes.addFlashAttribute("message", "ADMIN_UPDATED_SUCCESS");
            return "redirect:/super-admin/admin";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("adminId", adminId);
            model.addAttribute("adminUpdateRequest", e.getAdminData());
            model.addAttribute("error", e.getMessage());
            return "super-admin/update-admin";
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/super-admin/admin";
        }
    }

}
