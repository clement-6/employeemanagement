package tek.getarrays.employeemanagement.controller;



import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.entity.Employee;
import tek.getarrays.employeemanagement.services.EmployeeService;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService empService;

    @ApiOperation(value = "delete employee by id")
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){empService.delete(id);}

    @ApiOperation(value = "get all employee")
    @GetMapping("/list")
    public List<Employee> employeeByList(){return empService.listEmployee();}

    @ApiOperation(value = "get a specific employee")
    @GetMapping("By/{id}")
    public Employee employeeById(@PathVariable long id){
        return empService.employeeById(id);
    }

    @ApiOperation(value = "add employee")
    @PostMapping("/add")
    public ResponseEntity<Employee> add(@RequestBody EmployeeDTO employeeDTO){
        return new ResponseEntity<>(empService.add(employeeDTO), HttpStatus.CREATED);
    }
    @ApiOperation(value = "update employee")
    @PutMapping("/up/employeeId/{id}")
    public ResponseEntity<Employee> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable long id){
        return new ResponseEntity<>(empService.up(employeeDTO,id), HttpStatus.CREATED);
    }
    @ApiOperation(value = "generate document excel for all employee")
    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String  headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=course.xls";

        response.setHeader(headerKey,headerValue);

        empService.generateExcel(response);
    }

}
