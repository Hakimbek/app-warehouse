package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Employee;
import uz.pdp.appwarehouse.payload.EmployeeDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    // ADD
    @PostMapping
    public Result add(@RequestBody EmployeeDto employeeDto) {
        return employeeService.add(employeeDto);
    }

    // GET
    @GetMapping
    public List<Employee> get() {
        return employeeService.get();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return employeeService.delete(id);
    }

    // EDIT
    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody EmployeeDto employeeDto) {
        return employeeService.edit(id, employeeDto);
    }
}
