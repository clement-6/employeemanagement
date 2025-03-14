package tek.getarrays.employeemanagement.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tek.getarrays.employeemanagement.entity.Employee;
import tek.getarrays.employeemanagement.repository.EmployeeRepo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    void shouldGetAllEmployee(){
        List<Employee> employeeList = employeeRepo.findAll();
        assertEquals(3,employeeList.size());
        assertEquals("franck", employeeList.get(1).getName());
    }

    @Test
    void ShouldGetEmployeeById(){
        Employee employee = employeeRepo.findById(1L).get();

        assertEquals("daniel", employee.getName());
        assertEquals("daniel@gmail.com", employee.getEmail());
        assertEquals("testeur QA", employee.getJobTitle());
        assertEquals("691683541", employee.getPhone());
        assertEquals("yaounde", employee.getAddress());
        assertEquals("image1", employee.getImageUrl());
        assertEquals("AAAAA", employee.getCodeEmployee());
    }

    @Test
    void shouldSaveEmployee(){
        Employee employee = new Employee();
        employee.setName("fabrice");
        employee.setEmail("fabrice@gmail.com");
        employee.setJobTitle("Manager");
        employee.setPhone("693708210");
        employee.setAddress("Ecole de poste");
        employee.setImageUrl("NESF");
        employee.setCodeEmployee("WWW0000");

        Employee savedEmployee = employeeRepo.save(employee);

        assertNotNull(savedEmployee.getId());

        assertEquals("fabrice", employee.getName());
        assertEquals("fabrice@gmail.com", employee.getEmail());
        assertEquals("Manager", employee.getJobTitle());
        assertEquals("693708210", employee.getPhone());
        assertEquals("Ecole de poste", employee.getAddress());
        assertEquals("NESF", employee.getImageUrl());
        assertEquals("WWW0000", employee.getCodeEmployee());
    }

    @Test
    void ShouldUpdateEmployee(){
        Employee employee = employeeRepo.findById(1L).get();

        employee.setJobTitle("Analyste Programmeur");

        Employee savedEmployee = employeeRepo.save(employee);

        assertEquals("Analyste Programmeur", employee.getJobTitle());
    }

    @Test
    void ShouldDeleteEmployee(){
        employeeRepo.deleteById(3L);

        Optional<Employee> employee = employeeRepo.findById(3L);

        assertFalse(employee.isPresent());
    }

}