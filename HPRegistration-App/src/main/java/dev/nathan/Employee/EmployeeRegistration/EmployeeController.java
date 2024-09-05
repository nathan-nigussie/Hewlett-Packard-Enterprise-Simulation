package dev.nathan.Employee.EmployeeRegistration;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("")
    List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Employee findById(@PathVariable Integer id) {

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return employee.get();
    }

    //post employee data

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Employee employee) {
        employeeRepository.save(employee);
    }
   @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Integer id, @Valid @RequestBody Employee updatedEmployee) {
        if (!id.equals(updatedEmployee.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        // Create a new instance with updated values
        Employee newEmployee = new Employee(
                existingEmployee.id(),  // Use the existing id
                updatedEmployee.title() != null ? updatedEmployee.title() : existingEmployee.title(),
                updatedEmployee.full_name() != null ? updatedEmployee.full_name() : existingEmployee.full_name(),
                updatedEmployee.gender() != null ? updatedEmployee.gender() : existingEmployee.gender(),
                updatedEmployee.age() != null ? updatedEmployee.age() : existingEmployee.age(),
                updatedEmployee.email() != null ? updatedEmployee.email() : existingEmployee.email(),
                updatedEmployee.phone_number() != null ? updatedEmployee.phone_number() : existingEmployee.phone_number(),
                updatedEmployee.hire_date() != null ? updatedEmployee.hire_date() : existingEmployee.hire_date(),
                updatedEmployee.department() != null ? updatedEmployee.department() : existingEmployee.department()

        );

        // Save the updated employee
        employeeRepository.save(newEmployee);
    }

    //delete entry
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.delete(existingEmployee.get());
    }


    //filter employee by department
    @GetMapping("/department/{department}")
    List<Employee>findByDepartment(@PathVariable String department){
        return employeeRepository.findAllByDepartment(department);
    }
    //filter employee by gender
    @GetMapping("/gender/{gender}")
    List<Employee>findAllByGender(@PathVariable String gender){
        return employeeRepository.findAllByGender(gender);
    }

    // Endpoint to get all employees sorted by a specified field e.g. by email

   @GetMapping("/sorted-by-email")
   List<Employee> getAllEmployeesSortedByEmail() {
       return employeeRepository.findAllByOrderByEmailAsc();
   }
    // Endpoint to get all employees sorted by a specific field dynamically in asending order
    @GetMapping("/sorted")
    public List<Employee> getAllEmployeesSorted(@RequestParam String sortBy) {
        return employeeRepository.findAll(Sort.by(Sort.Order.asc(sortBy)));
    }
}