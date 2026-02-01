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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,
                                                   Locale locale,
                                                   RedirectAttributes redirectAttributes) {

        addErrorFlash(ex, locale, redirectAttributes);

        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("adminCreateRequest", ex.getData());
        }

        return "redirect:/super-admin/admin/create";
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public String handleAccountNotFoundException(AccountNotFoundException ex,
                                                 Locale locale,
                                                 RedirectAttributes redirectAttributes) {

        addErrorFlash(ex, locale, redirectAttributes);
        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("accountRequest", ex.getData());
        }
        return "redirect:/super-admin/admin";
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFoundException(TaskNotFoundException ex,
                                              Locale locale,
                                              RedirectAttributes redirectAttributes) {
        addErrorFlash(ex, locale, redirectAttributes);
        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("taskRequest", ex.getData());
        }
        return "redirect:/admin/task";
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public String handleProjectNotFoundException(ProjectNotFoundException ex,
                                                 Locale locale,
                                                 RedirectAttributes redirectAttributes) {
        addErrorFlash(ex, locale, redirectAttributes);
        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("projectRequest", ex.getData());
        }
        return "redirect:/admin/project";
    }

    @ExceptionHandler(CannotDeleteSuperAdminException.class)
    public String handleCannotDeleteSuperAdminException(CannotDeleteSuperAdminException ex,
                                                       Locale locale,
                                                       RedirectAttributes redirectAttributes) {
        addErrorFlash(ex, locale, redirectAttributes);
        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("deleteSuperAdminRequest", ex.getData());
        }
        return "redirect:/super-admin/admin";
    }

    @ExceptionHandler(CannotDeleteSelfException.class)
    public String handleCannotDeleteSelfException(CannotDeleteSelfException ex,
                                                  Locale locale,
                                                  RedirectAttributes redirectAttributes) {
        addErrorFlash(ex, locale, redirectAttributes);
        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("deleteSelfRequest", ex.getData());
        }
        return "redirect:/admin/user";
    }

    @ExceptionHandler(CannotDeleteAdminException.class)
    public String handleCannotDeleteAdminException(CannotDeleteAdminException ex,
                                                   Locale locale,
                                                   RedirectAttributes redirectAttributes) {
        addErrorFlash(ex, locale, redirectAttributes);
        if (ex.getData() != null) {
            redirectAttributes.addFlashAttribute("deleteAdminRequest", ex.getData());
        }
        return "redirect:/admin/user";
    }

    private void addErrorFlash(ApplicationException ex,
                               Locale locale,
                               RedirectAttributes redirectAttributes) {
        String msg = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
        redirectAttributes.addFlashAttribute("error", msg);
    }
}
