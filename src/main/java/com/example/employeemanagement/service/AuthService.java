package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Admin;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.entity.HR;
import com.example.employeemanagement.repository.AdminRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.HRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HRRepository hrRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Admin methods
    public Admin createAdmin(Admin admin) {
        // Check if admin with email already exists
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new RuntimeException("Admin with this email already exists");
        }
        // Encode password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public boolean validateAdmin(String email, String rawPassword) {
        var adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return passwordEncoder.matches(rawPassword, admin.getPassword());
        }
        return false;
    }

    // Employee methods
    public Employee createEmployee(Employee employee) {
        // Check if employee with email already exists
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new RuntimeException("Employee with this email already exists");
        }
        // Encode password
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public boolean validateEmployee(String email, String rawPassword) {
        var employeeOpt = employeeRepository.findByEmail(email);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            return passwordEncoder.matches(rawPassword, employee.getPassword());
        }
        return false;
    }

    // HR methods
    public HR createHR(HR hr) {
        // Check if HR with email already exists
        if (hrRepository.findByEmail(hr.getEmail()).isPresent()) {
            throw new RuntimeException("HR with this email already exists");
        }
        // Encode password
        hr.setPassword(passwordEncoder.encode(hr.getPassword()));
        return hrRepository.save(hr);
    }

    public boolean validateHR(String email, String rawPassword) {
        var hrOpt = hrRepository.findByEmail(email);
        if (hrOpt.isPresent()) {
            HR hr = hrOpt.get();
            return passwordEncoder.matches(rawPassword, hr.getPassword());
        }
        return false;
    }
}