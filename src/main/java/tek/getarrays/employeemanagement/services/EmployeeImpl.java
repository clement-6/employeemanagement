package tek.getarrays.employeemanagement.services;


import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.NotFoundException;
import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.dto.EmployeeResponseDto;
import tek.getarrays.employeemanagement.dto.EmployeeUpdateDto;
import tek.getarrays.employeemanagement.entity.Employee;
import tek.getarrays.employeemanagement.Exception.BadRequestException;

import tek.getarrays.employeemanagement.repository.EmployeeRepo;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.USER_EMAIL_EXIST;

@Service
@AllArgsConstructor
public class EmployeeImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    private final ModelMapper modelMapper;



    private Employee findEmployee(long id){
        return employeeRepo.findById(id).orElseThrow(()-> new NotFoundException("Employee with ID"+ id + "not found"));
    }

    private void checkEmployeeByEmail(EmployeeDTO employeeDTO){
        if (employeeRepo.existsByEmail(employeeDTO.getEmail())){
            throw new BadRequestException(USER_EMAIL_EXIST);
        }
    }


    @Override
    public EmployeeResponseDto add(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        checkEmployeeByEmail(employeeDTO);
        employee.setMatriculEmployee(UUID.randomUUID().toString());
        employeeRepo.save(employee);
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

    @Override
    public EmployeeResponseDto up(EmployeeUpdateDto employeeDTO, long id) {
        Employee employee = findEmployee(id);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(employeeDTO, employee);
        employee.setId(id);
        employeeRepo.save(employee);
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

    @Override
    public void delete(long id) {
        if (!employeeRepo.existsById(id)){
            throw new NotFoundException("Employee with ID"+ id + "not found");
        }
        employeeRepo.deleteById(id);
    }

    @Override
    public List<EmployeeResponseDto> listEmployee() {
        List<Employee> employeeList = employeeRepo.findAll();
        return employeeList.stream().map(list -> modelMapper.map(list, EmployeeResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDto> listEmployeeByJob(String jobTitle) {
        List<Employee> employeeList = employeeRepo.findByJobTitle(jobTitle);
        return employeeList.stream().map(list -> modelMapper.map(list, EmployeeResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDto employeeById(long id) {
        Employee employee = findEmployee(id);
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {

        List<Employee> employee = employeeRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employee Liste");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Code");
        row.createCell(1).setCellValue("Nom");
        row.createCell(2).setCellValue("Poste");
        row.createCell(3).setCellValue("Telephone");
        row.createCell(4).setCellValue("adresse");
        row.createCell(5).setCellValue("email");



        int dataRowIndex = 1;

        for(Employee emp : employee){
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(emp.getMatriculEmployee());
            dataRow.createCell(1).setCellValue(emp.getName());
            dataRow.createCell(2).setCellValue(emp.getJobTitle());
            dataRow.createCell(3).setCellValue(emp.getPhone());
            dataRow.createCell(4).setCellValue(emp.getAddress());
            dataRow.createCell(5).setCellValue(emp.getEmail());
            dataRowIndex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();

    }
}
