package tek.getarrays.employeemanagement.employee.Controller;



import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tek.getarrays.employeemanagement.employee.DTO.EmployeeDTO;
import tek.getarrays.employeemanagement.employee.Entity.Employee;
import tek.getarrays.employeemanagement.employee.Services.service.EmployeeService;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService empService;

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){empService.delete(id);}

    @GetMapping("/list")
    public List<Employee> employeeByList(){return empService.listEmployee();}

    @GetMapping("By/{id}")
    public Employee employeeById(@PathVariable long id){
        return empService.employeeById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> add(@RequestBody EmployeeDTO employeeDTO){
        return new ResponseEntity<>(empService.add(employeeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/up/employeeId/{id}")
    public ResponseEntity<Employee> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable long id){
        return new ResponseEntity<>(empService.up(employeeDTO,id), HttpStatus.CREATED);
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String  headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=course.xls";

        response.setHeader(headerKey,headerValue);

        empService.generateExcel(response);
    }

}
