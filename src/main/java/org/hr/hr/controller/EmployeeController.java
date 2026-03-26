package org.hr.hr.controller;

import jakarta.validation.Valid;
import org.hr.hr.dto.EmployeeRequest;
import org.hr.hr.model.EmployeeModel;
import org.hr.hr.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

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

    @GetMapping("/page")
    public Page<EmployeeModel> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return employeeService.findPage(page, size);
    }

    @PostMapping
    public EmployeeModel create(@Valid @RequestBody EmployeeRequest request){
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
