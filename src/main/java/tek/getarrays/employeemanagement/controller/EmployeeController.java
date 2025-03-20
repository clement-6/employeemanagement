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
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService empService;

    @ApiOperation(value = "delete employee by id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id){empService.delete(id);}

    @ApiOperation(value = "get all employee")
    @GetMapping("/list")
    public List<EmployeeDTO> employeeByList(){return empService.listEmployee();}
    @ApiOperation(value = "get all employee by title job")
    @GetMapping("/list/JobTitle")
    public List<EmployeeDTO> employeeByJobTitle(@RequestParam String jobTitle){return empService.listEmployeeByJob(jobTitle);}

    @ApiOperation(value = "get a specific employee")
    @GetMapping("/by/{id}")
    public ResponseEntity<EmployeeDTO> employeeById(@PathVariable long id){return new ResponseEntity<>(empService.employeeById(id),HttpStatus.OK);}

    @ApiOperation(value = "add employee")
    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> add(@Valid @RequestBody EmployeeDTO employeeDTO){
        return new ResponseEntity<>(empService.add(employeeDTO), HttpStatus.CREATED);
    }
    @ApiOperation(value = "update employee")
    @PutMapping("/up/employeeId/{id}")
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable long id){
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
