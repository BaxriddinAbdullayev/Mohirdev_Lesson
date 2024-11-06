package uz.mohirdev.lesson.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.mohirdev.lesson.entity.Employee;
import uz.mohirdev.lesson.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity create(@RequestBody Employee employee){
        Employee result= employeeService.save(employee);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/employees")
    public ResponseEntity findAll(){
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/search")
    public ResponseEntity findAllByQueryParam(@RequestParam String name){
        List<Employee> employees = employeeService.findByQueryParam(name);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees")
    public ResponseEntity update(@RequestBody Employee employee){
        if(employee.getId()==null){
            return ResponseEntity.ok("Error");
        }
        Employee result= employeeService.save(employee);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok("Qator o'chirildi");
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }


}
