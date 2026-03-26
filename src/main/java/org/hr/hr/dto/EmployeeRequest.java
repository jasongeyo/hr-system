package org.hr.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeRequest {

    @NotBlank(message = "empId 不可空白")
    @Size(max = 10, message = "empid 不可超過 10 字")
    private String empId;

    @NotBlank(message = "name 不可空白")
    @Size(max = 50, message = "name 不可超過 10 字")
    private String name;

    @NotBlank(message = "deptNo 不可空白")
    @Size(max = 20, message = "deptNo 不可超過 20 字")
    private String deptNo;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
}
