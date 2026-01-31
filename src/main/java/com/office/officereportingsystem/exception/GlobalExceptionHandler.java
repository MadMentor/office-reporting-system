package com.office.officereportingsystem.exception;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException ex, Locale locale,
                                                   RedirectAttributes redirectAttributes) {

        String msg = messageSource.getMessage(ex.getMessage(), null, locale);

        redirectAttributes.addFlashAttribute("error", msg);
        redirectAttributes.addFlashAttribute("adminCreateRequest", ex.getAdminData());

        return "redirect:/super-admin/admin/create";
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public String handleAccountNotFoundException(AccountNotFoundException ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/super-admin/admin";
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFoundException(TaskNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/task";
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public String handleProjectNotFoundException(ProjectNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/project";
    }
}
