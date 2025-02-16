package tek.getarrays.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tek.getarrays.employeemanagement.Entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

}
