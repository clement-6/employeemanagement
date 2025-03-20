package tek.getarrays.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tek.getarrays.employeemanagement.entity.Employee;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    boolean existsByEmail(String email);
    List<Employee> findByJobTitle(String jobTitle);
}
