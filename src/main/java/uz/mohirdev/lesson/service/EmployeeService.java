package uz.mohirdev.lesson.service;

import org.springframework.stereotype.Service;
import uz.mohirdev.lesson.entity.Employee;
import uz.mohirdev.lesson.ripository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id){
        Optional<Employee> optional=employeeRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public List<Employee> findAll(){
        List<Employee> employees=employeeRepository.findAll();
        return employees;
    }

    public List<Employee> findByQueryParam(String name){
        return employeeRepository.findAllByNameLike(name);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
