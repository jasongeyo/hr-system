package org.hr.hr.controller;

import jakarta.validation.Valid;
import org.hr.hr.dto.EmployeeRequest;
import org.hr.hr.model.EmployeeModel;
import org.hr.hr.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeModel> getAll() {
        return employeeService.findAll();
    }

    @PostMapping
    public EmployeeModel create(@Valid @RequestBody EmployeeRequest request){
        System.err.println("===");
        return employeeService.create(request);
    }

    @PutMapping("/update")
    public EmployeeModel update(@RequestBody EmployeeModel model){
        return employeeService.save(model);
    }

    @DeleteMapping("/{empId}")
    public void delete(@PathVariable String empId){
        employeeService.deleteById(empId);
    }
}
