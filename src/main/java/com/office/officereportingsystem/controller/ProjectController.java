package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.ProjectCreateRequestDto;
import com.office.officereportingsystem.dto.request.ProjectUpdateRequestDto;
import com.office.officereportingsystem.entity.Project;
import com.office.officereportingsystem.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // List projects
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/projects")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
//        model.addAttribute("message", message);
        return "project/list-projects";
    }

    // Open create project form
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/project/create")
    public String openCreateProjectForm(Model model) {
        if (!model.containsAttribute("projectCreateRequest")) {
            model.addAttribute("projectCreateRequest", new ProjectCreateRequestDto());
        }
        return "project/create-project";
    }

    // Create project
    @PostMapping("/project/save")
    public String createProject(@Valid @ModelAttribute("projectCreateRequest") ProjectCreateRequestDto dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.projectCreateRequest", result);
            redirectAttributes.addFlashAttribute("projectCreateRequest", dto);
            return "redirect:/admin/project/create";
        }

        projectService.createProject(dto, principal.getName());
        redirectAttributes.addFlashAttribute("message", "PROJECT_CREATED_SUCCESS");
        return "redirect:/admin/projects";
    }

    // Edit project
    @GetMapping("/project/edit/{id}")
    public String editProject(@PathVariable Integer id, Model model) {
        ProjectUpdateRequestDto dto = projectService.getProjectById(id);
        model.addAttribute("projectUpdateRequest", dto);
        model.addAttribute("projectId", id);
        return "project/update-project";
    }

    // Update project
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/project/update/{id}")
    public String updateProject(@PathVariable Integer id,
                                @Valid @ModelAttribute("projectUpdateRequest") ProjectUpdateRequestDto dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("projectId", id);
            return "project/update-project";
        }

        projectService.updateProject(id, dto);
        redirectAttributes.addFlashAttribute("message", "PROJECT_UPDATED_SUCCESS");
        return "redirect:/admin/projects";
    }

    // Delete project
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("message", "PROJECT_DELETED_SUCCESS");
        return "redirect:/admin/projects";
    }
}
