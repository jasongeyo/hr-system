package org.hr.hr.service;

import org.hr.hr.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmloyeeService {

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();

        list.add(new Employee(1L , "Jason" , "HR"));
        list.add(new Employee(2L , "Amy" , "Finance"));

        return list;
    }
}
