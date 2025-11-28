package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.HR;
import com.example.employeemanagement.repository.HRRepository;
import com.example.employeemanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hr")
@CrossOrigin(origins = "http://localhost:5173")
public class HRController {

    @Autowired
    private AuthService authService;

    @Autowired
    private HRRepository hrRepository;


    // ---------------------------- CREATE HR ----------------------------
    @PostMapping("/create")
    public ResponseEntity<?> createHR(@RequestBody HR hr) {
        try {
            HR created = authService.createHR(hr);

            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "HR created successfully",
                            "data", created
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "Error creating HR: " + e.getMessage()
                    )
            );
        }
    }


    // ----------------------------- LOGIN -------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> loginHR(@RequestBody Map<String, String> loginReq) {

        String email = loginReq.get("email");
        String password = loginReq.get("password");

        boolean isValid = authService.validateHR(email, password);

        if (isValid) {
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "HR logged in successfully",
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
    @GetMapping
    public ResponseEntity<?> getAllHR() {
        List<HR> list = hrRepository.findAll();
        return ResponseEntity.ok(Map.of("status", "success", "count", list.size(), "data", list));
    }


}