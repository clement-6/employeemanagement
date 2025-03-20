package tek.getarrays.employeemanagement.services;


import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tek.getarrays.employeemanagement.Exception.NotFoundException;
import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.entity.Employee;
import tek.getarrays.employeemanagement.Exception.BadRequestException;

import tek.getarrays.employeemanagement.repository.EmployeeRepo;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;



    private Employee findEmployee(long id){
        return employeeRepo.findById(id).orElseThrow(()-> new NotFoundException("Employee with ID"+ id + "not found"));
    }

    private void getEmployee(Employee employee, EmployeeDTO employeeDTO){
        if (employeeRepo.existsByEmail(employeeDTO.getEmail())){
            throw new BadRequestException("l'email existe deja");
        }
        employee.setName(employeeDTO.getName());
        if (!StringUtils.hasText(employeeDTO.getName())){
            throw new BadRequestException("name is required");
        }
        employee.setEmail(employeeDTO.getEmail());
        if (!StringUtils.hasText(employeeDTO.getEmail())){
            throw new BadRequestException("Email is required");
        }
        employee.setJobTitle(employeeDTO.getJobTitle());
        employee.setPhone(employeeDTO.getPhone());
        employee.setAddress(employeeDTO.getAddress());
    }

    @Override
    public EmployeeDTO add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        getEmployee(employee,employeeDTO);
        employee.setMatriculEmployee(UUID.randomUUID().toString());
        employeeRepo.save(employee);
        return mapToDTO(employee);
    }

    @Override
    public EmployeeDTO up(EmployeeDTO employeeDTO, long id) {
        Employee employee = findEmployee(id);
        getEmployee(employee,employeeDTO);
        employee.setMatriculEmployee(employee.getMatriculEmployee());
        employeeRepo.save(employee);
        return mapToDTO(employee);
    }

    @Override
    public void delete(long id) {
        if (!employeeRepo.existsById(id)){
            throw new NotFoundException("Employee with ID"+ id + "not found");
        }
        employeeRepo.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> listEmployee() {
        List<Employee> employeeList = employeeRepo.findAll();
        return employeeList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> listEmployeeByJob(String jobTitle) {
        List<Employee> employeeList = employeeRepo.findByJobTitle(jobTitle);
        return employeeList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO employeeById(long id) {
        Employee employee = findEmployee(id);
        return mapToDTO(employee);
    }

    private EmployeeDTO mapToDTO(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setJobTitle(employee.getJobTitle());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setMatriculEmployee(employee.getMatriculEmployee());
        dto.setCreateDate(employee.getCreateDate());
        dto.setUpdateDate(employee.getUpdateDate());
        return  dto;
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
