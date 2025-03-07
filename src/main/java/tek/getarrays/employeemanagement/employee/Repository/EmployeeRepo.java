package tek.getarrays.employeemanagement.employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tek.getarrays.employeemanagement.employee.Entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

}
