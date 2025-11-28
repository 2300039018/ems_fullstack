package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.Admin;
import com.example.employeemanagement.repository.AdminRepository;
import com.example.employeemanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminRepository AdminRepository;


    // -------------------------- CREATE ADMIN --------------------------
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
        try {
            Admin created = authService.createAdmin(admin);

            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "Admin created successfully",
                            "data", created
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "Error creating admin: " + e.getMessage()
                    )
            );
        }
    }


    // ---------------------------- LOGIN -------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> loginReq) {

        String email = loginReq.get("email");
        String password = loginReq.get("password");

        boolean isValid = authService.validateAdmin(email, password);

        if (isValid) {
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "Admin logged in successfully",
                            "email", email
                    )
            );
        }

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", "error",
                        "message", "Invalid credentials"
                )
        );
    }


}