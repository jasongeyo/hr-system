package org.hr.hr;

import org.hr.hr.entity.Employee;
import org.hr.hr.model.EmployeeModel;
import org.hr.hr.repository.EmployeeRepository;
import org.hr.hr.service.EmployeeService;
import org.hr.hr.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RedisService redisService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        emp1 = new Employee();
        emp1.setEmpId("E001");
        emp1.setName("Jason");
        emp1.setDeptNo("D01");

        emp2 = new Employee();
        emp2.setEmpId("E002");
        emp2.setName("Amy");
        emp2.setDeptNo("D02");
    }

    @Test
    void findAll_shouldReturnAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(emp1, emp2));

        List<EmployeeModel> result = employeeService.findAll();

        assertEquals(2, result.size());
        assertEquals("Jason", result.get(0).getName());
        assertEquals("Amy", result.get(1).getName());
    }

    @Test
    void search_withDeptNo_shouldReturnFilteredEmployees() {
        when(employeeRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(emp1));

        List<EmployeeModel> result = employeeService.search("D01", null);

        assertEquals(1, result.size());
        assertEquals("D01", result.get(0).getDeptNo());
    }

    @Test
    void search_withName_shouldReturnFilteredEmployees() {
        when(employeeRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(emp1));

        List<EmployeeModel> result = employeeService.search(null, "Ja");

        assertEquals(1, result.size());
        assertEquals("Jason", result.get(0).getName());
    }

    @Test
    void search_withBothConditions_shouldReturnFilteredEmployees() {
        when(employeeRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(emp1));

        List<EmployeeModel> result = employeeService.search("D01", "Ja");

        assertEquals(1, result.size());
        assertEquals("E001", result.get(0).getEmpId());
    }

    @Test
    void findPage_shouldReturnPagedResult() {
        Page<Employee> page = new PageImpl<>(List.of(emp1, emp2));
        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<EmployeeModel> result = employeeService.findPage(0, 10);

        assertEquals(2, result.getTotalElements());
        assertEquals("Jason", result.getContent().get(0).getName());
    }
}
