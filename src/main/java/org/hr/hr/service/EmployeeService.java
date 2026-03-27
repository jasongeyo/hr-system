package org.hr.hr.service;

import org.apache.catalina.User;
import org.hr.hr.dto.EmployeeRequest;
import org.hr.hr.entity.Employee;
import org.hr.hr.model.EmployeeModel;
import org.hr.hr.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RedisService redisService;

    public EmployeeService(EmployeeRepository employeeRepository, RedisService redisService){
        this.employeeRepository = employeeRepository;
        this.redisService = redisService;
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

    //
    public List<EmployeeModel> search (String deptNo, String name){
        List<Employee> entities;

        if (deptNo != null ){
            entities = employeeRepository.findByDeptNo(deptNo);
        }
        else if  (name != null){
            entities = employeeRepository.findByNameContaining(name);
        }
        else {
            entities = employeeRepository.findAll();
        }

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

    //分頁
    public Page<EmployeeModel> findPage(int page, int size) {
        return employeeRepository.findAll(PageRequest.of(page, size))
                .map(e -> {
                    EmployeeModel m = new EmployeeModel();
                    m.setEmpId(e.getEmpId());
                    m.setName(e.getName());
                    m.setDeptNo(e.getDeptNo());
                    return m;
                });
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

    public EmployeeModel create(EmployeeRequest request){
        Employee entity = new Employee();
        entity.setEmpId(request.getEmpId());
        entity.setName(request.getName());
        entity.setDeptNo(request.getDeptNo());

        Employee saved = employeeRepository.save(entity);
        EmployeeModel result = new EmployeeModel();
        result.setEmpId(saved.getEmpId());
        result.setName(saved.getName());
        result.setDeptNo(saved.getDeptNo());

        return result;
    }

    public EmployeeModel getEmployee(Long id) {
        //查詢Redis
        try {
            EmployeeModel cachedEmployee = (EmployeeModel) redisService.get("user:" + id);

            if (cachedEmployee != null) {
                System.out.println("--- 從 Redis 取得資料 (Cache Hit) ---");
                return cachedEmployee;
            }
        } catch (Exception e) {
            System.out.println("--- Redis 無法連線，直接查DB ---");
        }

        //查詢DB
        System.out.println("--- 從 DB 取得資料 (Cache Miss) ---");
        Employee employee = employeeRepository.findById(String.valueOf(id)).orElse(null);
        if (employee == null) {
            return null;
        }

        // 3. 手動轉換 (Mapping): 把 Employee 轉成 EmployeeModel
        EmployeeModel model = new EmployeeModel();
        BeanUtils.copyProperties(employee, model);

        try {
            redisService.set("user:" + id, model, 10, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println("--- Redis 寫入失敗，略過 ---");
        }

        return model;
    }
}
