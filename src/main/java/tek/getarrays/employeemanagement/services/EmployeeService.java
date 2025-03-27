package tek.getarrays.employeemanagement.services;


import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.dto.EmployeeResponseDto;
import tek.getarrays.employeemanagement.dto.EmployeeUpdateDto;



import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto add(EmployeeDTO employeeDTO);
    EmployeeResponseDto up(EmployeeUpdateDto employeeDTO, long id);
    void delete(long id);
    List<EmployeeResponseDto> listEmployee();

    List<EmployeeResponseDto> listEmployeeByJob(String jobTitle);
    EmployeeResponseDto employeeById(long id);

    void generateExcel(HttpServletResponse response) throws IOException;
}
