package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.AccountCreateRequestDto;
import com.office.officereportingsystem.dto.request.AccountUpdateRequestDto;
import com.office.officereportingsystem.exception.AccountNotFoundException;
import com.office.officereportingsystem.exception.UserAlreadyExistsException;
import com.office.officereportingsystem.service.AccountService;
import com.office.officereportingsystem.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final AdminService adminService;

    public AdminController(AccountService accountService, AdminService adminService) {
        this.accountService = accountService;
        this.adminService = adminService;
    }

    @GetMapping("/user/create")
    public String openCreateUser(Model model) {
        if (!model.containsAttribute("userCreateRequest")) {
            model.addAttribute("userCreateRequest", new AccountCreateRequestDto());
        }
        return "admin/create-user";
    }

    @PostMapping("/user/save")
    public String createUser(
            @Valid @ModelAttribute("userCreateRequest") AccountCreateRequestDto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userCreateRequest",
                    result
            );
            redirectAttributes.addFlashAttribute("userCreateRequest", dto);
            return "redirect:/admin/user/create";
        }

        try {
            accountService.createUser(dto);
            redirectAttributes.addFlashAttribute("message", "USER_CREATED_SUCCESS");
            return "redirect:/dashboard";
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("userCreateRequest", dto);
            return "redirect:/admin/user/create";
        }
    }

    @GetMapping("/user")
    public String listUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin/list-users";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes,
            Principal principal) {
        try {
            accountService.deleteAccount(id, principal.getName());
            redirectAttributes.addFlashAttribute("message", "USER_DELETED_SUCCESS");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model) throws IOException {
        model.addAttribute("userUpdateRequest", adminService.getUserById(id));
        model.addAttribute("userId", id);
        return "admin/update-user";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(
            @PathVariable("id") Integer userId,
            @Valid @ModelAttribute("userUpdateRequest") AccountUpdateRequestDto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("userId", userId);
            return "admin/update-user";
        }

        try {
            accountService.updateAccount(userId, dto);
            redirectAttributes.addFlashAttribute("message", "USER_UPDATED_SUCCESS");
            return "redirect:/admin/user";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("userId", userId);
            model.addAttribute("adminUpdateRequest", e.getAdminData());
            model.addAttribute("error", e.getMessage());
            return "super-admin/update-admin";
        } catch (AccountNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/user";
        }
    }
}
