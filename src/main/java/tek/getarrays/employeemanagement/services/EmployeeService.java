package tek.getarrays.employeemanagement.services;


import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.entity.Employee;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO add(EmployeeDTO employeeDTO);
    EmployeeDTO up(EmployeeDTO employeeDTO, long id);
    void delete(long id);
    List<EmployeeDTO> listEmployee();

    List<EmployeeDTO> listEmployeeByJob(String jobTitle);
    EmployeeDTO employeeById(long id);

    void generateExcel(HttpServletResponse response) throws IOException;
}
