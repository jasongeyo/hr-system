package org.hr.hr.repository;

import org.hr.hr.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpec {
    public static Specification<Employee> hasDeptNo(String deptNo){
        return (root, query, cb) ->
                deptNo == null ? null : cb.equal(root.get("deptNo"), deptNo);
    }

    public static Specification<Employee> nameContains (String name){
        return (root, query, cb) ->
                name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }
}
