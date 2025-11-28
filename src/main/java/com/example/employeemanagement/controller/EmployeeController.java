package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeRepository employeeRepository;


    // ------------------------- CREATE EMPLOYEE -----------------------------
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            Employee created = authService.createEmployee(employee);

            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "Employee created successfully",
                            "data", created
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "Error creating employee: " + e.getMessage()
                    )
            );
        }
    }


    // ----------------------------- LOGIN ----------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee(@RequestBody Map<String, String> loginReq) {

        String email = loginReq.get("email");
        String password = loginReq.get("password");

        boolean isValid = authService.validateEmployee(email, password);

        if (isValid) {
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "Employee logged in successfully",
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


    // ----------------------------- GET ALL --------------------------------
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> list = employeeRepository.findAll();
        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "count", list.size(),
                        "data", list
                )
        );
    }


    // ------------------------- GET PROFILE --------------------------------
    @GetMapping("/profile")
    public ResponseEntity<?> getEmployeeProfile(@RequestParam String email) {

        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isPresent()) {
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "data", employee.get()
                    )
            );
        }

        return ResponseEntity.status(404).body(
                Map.of(
                        "status", "error",
                        "message", "Employee not found"
                )
        );
    }
}