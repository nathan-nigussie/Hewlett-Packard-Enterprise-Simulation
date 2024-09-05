package dev.nathan.Employee.EmployeeRegistration;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface EmployeeRepository extends ListCrudRepository<Employee,Integer> {

    // filtering all employees with department field
    List<Employee> findAllByDepartment(String department);
    // filtering all employees with gender
    List<Employee> findAllByGender(String gender);

    // Retrieve all employees sorted by their email in ascending order
    List<Employee> findAllByOrderByEmailAsc();

    // Retrieve all employees dynamically sorted with any required fields
    List<Employee> findAll(Sort sort);

}
