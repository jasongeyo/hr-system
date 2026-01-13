package org.hr.hr.service;

import org.hr.hr.entity.Employee;
import org.hr.hr.model.EmployeeModel;
import org.hr.hr.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    //GET
    public List<EmployeeModel> findAll(){
        List<Employee> entities = employeeRepository.findAll();
        List<EmployeeModel> result = new ArrayList<>();

        for (Employee e : entities) {
            EmployeeModel m = new EmployeeModel();
            m.setEmpId(e.getEmpId());
            m.setName(e.getName());
            m.setDeptNo(e.getDeptNo());
            result.add(m);
        }
        return result;
    }

    //POST/PUT
    public EmployeeModel save(EmployeeModel model){
        Employee entity = new Employee();
        entity.setEmpId(model.getEmpId());
        entity.setName(model.getName());
        entity.setDeptNo(model.getDeptNo());

        Employee saved = employeeRepository.save(entity);

        EmployeeModel result = new EmployeeModel();
        result.setEmpId(saved.getEmpId());
        result.setName(saved.getName());
        result.setDeptNo(saved.getDeptNo());

        return result;
    }

    //DELETE
    public void deleteById(String empId){
        employeeRepository.deleteById(empId);
    }


}
