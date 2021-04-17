package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Employee;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.EmployeeDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.EmployeeRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    // ADD
    public Result add(EmployeeDto employeeDto) {
        boolean existsByPhoneNumber = employeeRepository.existsByPhoneNumber(employeeDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("Employee already exist", false);
        }

        List<Warehouse> warehouseList = warehouseRepository.findAllById(employeeDto.getWarehouseId());
        Set<Warehouse> warehouses = new LinkedHashSet<>(warehouseList);

        String code = UUID.randomUUID().toString();

        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPassword(employeeDto.getPassword());
        employee.setCode(code);
        employee.setWarehouse(warehouses);
        employeeRepository.save(employee);
        return new Result("Successfully added", true);
    }

    // GET
    public List<Employee> get() {
        return employeeRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) {
            return new Result("Employee not found", false);
        }

        return new Result("Success", true, optionalEmployee.get());
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            employeeRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, EmployeeDto employeeDto) {
        boolean existsByPhoneNumber = employeeRepository.existsByPhoneNumber(employeeDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("Employee already exist", false);
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (!optionalEmployee.isPresent()) {
            return new Result("Employee not found", false);
        }

        List<Warehouse> warehouseList = warehouseRepository.findAllById(employeeDto.getWarehouseId());
        Set<Warehouse> warehouses = new LinkedHashSet<>(warehouseList);

        Employee employee = optionalEmployee.get();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPassword(employeeDto.getPassword());
        employee.setWarehouse(warehouses);
        employee.setActive(employeeDto.isActive());
        employeeRepository.save(employee);
        return new Result("Successfully edited", true);
    }
}
