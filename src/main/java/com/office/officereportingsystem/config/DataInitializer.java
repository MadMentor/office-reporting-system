package com.office.officereportingsystem.config;

import com.office.officereportingsystem.entity.User;
import com.office.officereportingsystem.enums.Role;
import com.office.officereportingsystem.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0) {
            User superAdmin = new User();
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setEmail("admin@gmail.com");
            superAdmin.setPassword(passwordEncoder.encode("admin@123"));
            superAdmin.setRole(Role.SUPER_ADMIN);
            userRepo.save(superAdmin);
            System.out.println("Superadmin created with username 'admin' and password 'admin123'");
        }
    }
}
