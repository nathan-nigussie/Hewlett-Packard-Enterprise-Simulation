package dev.nathan.Employee.EmployeeRegistration;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.hasSize;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    EmployeeRepository employeeRepository;
private final List<Employee> employee=new ArrayList<>();
@BeforeEach
    void setUp(){
    employee.add(new Employee(1,"Mr","David","Male",25,"davidL@gmail.com",212633, LocalDateTime.now(),"Engineering"));
}
@Test
    void shouldFindAllEmployees() throws Exception {
    when(employeeRepository.findAll()).thenReturn(employee);
    mvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(employee.size())));

}

//find Single Employee with valid ID
    @Test
    void shouldFindOneEmployee() throws Exception {
        Employee employee1 = employee.get(0);
        when(employeeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(employee1));
        mvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employee1.id())));

    }
    //find Single Employee with Invalid ID
    @Test
    void shouldReturnNotFoundWithInvalidId() throws Exception {
        mvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound());
    }
    @Test
    void shouldCreateNewEmployee() throws Exception {
        var employee = new Employee(1,"Mr","Maria","Female",26,"maria@gmail.com",26565,LocalDateTime.now(),"marketing");
        mvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee))
                )
                .andExpect(status().isCreated());
    }

    //Update Entry for single employee

    @Test
    void shouldUpdateEmployee() throws Exception {
        var existingEmployee = new Employee(1, "Mr", "Maria", "Female", 26, "maria@gmail.com", 26565, LocalDateTime.now(), "Engineering");
        var updatedEmployee = new Employee(1, "Mr", "Maria", "Female", 27, "maria.updated@gmail.com", 12345, LocalDateTime.now(), "Marketing");
        when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));

        mvc.perform(MockMvcRequestBuilders.put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee))
                )
              .andExpect(status().isNoContent());
    }
    //Delete single employee with Valid ID
    @Test
    public void shouldDeleteEmployee() throws Exception {
        // Mocking that the employee exists
        Employee mockEmployee = new Employee(1, "Mr", "Maria", "Female", 26, "maria@gmail.com", 26565, LocalDateTime.now(), "Engineering"); // Creating a mock Employee object

        // Mocking that the employee exists
        when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));

        // Mocking the delete method to do nothing
        doNothing().when(employeeRepository).delete(mockEmployee);

        // Performing the delete request and expecting no content
        mvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());

        // Verifying that deleteById was called
        verify(employeeRepository, times(1)).delete(mockEmployee);
    }

}