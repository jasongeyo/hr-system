package org.hr.hr.controller;

import org.hr.hr.model.Employee;
import org.hr.hr.service.EmloyeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmloyeeController {
    private final EmloyeeService emloyeeService;

    public EmloyeeController(EmloyeeService emloyeeService) {
        this.emloyeeService = emloyeeService;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return emloyeeService.findAll();
    }
}
