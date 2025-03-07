package tek.getarrays.employeemanagement.employee.Services.service;


import tek.getarrays.employeemanagement.employee.DTO.EmployeeDTO;
import tek.getarrays.employeemanagement.employee.Entity.Employee;


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
