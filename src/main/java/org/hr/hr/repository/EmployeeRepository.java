package org.hr.hr.repository;


import org.hr.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> , JpaSpecificationExecutor<Employee> {
    List<Employee> findByDeptNo(String deptNo);
    List<Employee> findByNameContaining(String name);
}
