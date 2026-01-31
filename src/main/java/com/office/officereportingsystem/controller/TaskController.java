package com.office.officereportingsystem.controller;

import com.office.officereportingsystem.dto.request.TaskCreateRequestDto;
import com.office.officereportingsystem.dto.request.TaskUpdateRequestDto;
import com.office.officereportingsystem.dto.response.TaskResponseDto;
import com.office.officereportingsystem.entity.Task;
import com.office.officereportingsystem.enums.TaskStatus;
import com.office.officereportingsystem.service.ProjectService;
import com.office.officereportingsystem.service.TaskService;
import com.office.officereportingsystem.service.UserService;
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
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    // Open task list
    @GetMapping("/all-tasks")
    public String listTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task/list-tasks";
    }

    @GetMapping("/my-task")
    public String listMyTasks(Model model, Principal principal) {
        List<Task> tasks = taskService.getTasksAssignedToUser(principal.getName());
        model.addAttribute("tasks", tasks);
        return "task/list-tasks";
    }

    // Open create task form
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/create")
    public String openCreateTaskForm(Model model) {
        if (!model.containsAttribute("taskCreateRequest")) {
            model.addAttribute("taskCreateRequest", new TaskCreateRequestDto());
        }

        // Pass list of users for assignment dropdown
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        return "task/create-task";
    }

    // Create task
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/save")
    public String createTask(@Valid @ModelAttribute("taskCreateRequest") TaskCreateRequestDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskCreateRequest", result);
            redirectAttributes.addFlashAttribute("taskCreateRequest", dto);
            return "redirect:/task/create";
        }

        taskService.createTask(dto, principal.getName());
        redirectAttributes.addFlashAttribute("message", "TASK_CREATED_SUCCESS");
        return "redirect:/task/all-tasks";
    }


    // Open edit task form
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Integer id, Model model) {
        model.addAttribute("taskUpdateRequest", taskService.getTaskUpdateRequestById(id));
        model.addAttribute("taskId", id);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("projects", projectService.getAllProjects());
        return "task/update-task";
    }

    // Update task
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Integer id,
                             @Valid @ModelAttribute("taskUpdateRequest") TaskUpdateRequestDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model,
                             Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("taskId", id);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("projects", projectService.getAllProjects());
            return "task/update-task";
        }

        taskService.updateTask(id, dto, principal.getName());
        redirectAttributes.addFlashAttribute("message", "TASK_UPDATED_SUCCESS");
        return "redirect:/task/all-tasks";
    }

    // Delete task
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        taskService.deleteTask(id, principal.getName());
        redirectAttributes.addFlashAttribute("message", "TASK_DELETED_SUCCESS");
        return "redirect:/task/all-tasks";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Integer id,
                               @RequestParam TaskStatus to,
                               Principal principal,
                               RedirectAttributes ra) {

        taskService.changeTaskStatus(id, principal.getName(), to);
        ra.addFlashAttribute("message", "TASK_STATUS_UPDATED");
        return "redirect:/dashboard";
    }
}
