package tek.getarrays.employeemanagement.services;


import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.entity.Employee;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    Employee add(EmployeeDTO employeeDTO);
    Employee up(EmployeeDTO employeeDTO, long id);
    void delete(long id);
    List<Employee> listEmployee();
    Employee employeeById(long id);

    void generateExcel(HttpServletResponse response) throws IOException;
}
