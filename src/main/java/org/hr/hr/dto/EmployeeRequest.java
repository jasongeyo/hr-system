package org.hr.hr.dto;

import jakarta.validation.constraints.NotBlank;

public class EmployeeRequest {

    @NotBlank(message = "empId 不可空白")
    private String empId;

    @NotBlank(message = "name 不可空白")
    private String name;

    @NotBlank(message = "deptNo 不可空白")
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
