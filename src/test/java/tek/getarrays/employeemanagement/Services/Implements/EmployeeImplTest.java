package tek.getarrays.employeemanagement.Services.Implements;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tek.getarrays.employeemanagement.dto.EmployeeDTO;
import tek.getarrays.employeemanagement.entity.Employee;
import tek.getarrays.employeemanagement.Exception.BadRequestException;
import tek.getarrays.employeemanagement.repository.EmployeeRepo;
import tek.getarrays.employeemanagement.services.EmployeeImpl;


import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class EmployeeImplTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private EmployeeDTO employeeDTO;

    @InjectMocks
    private EmployeeImpl employee;

    @Test
    void shouldGetReturnAllEmployee(){
        Employee employee1 = new Employee(1L,"daniel","daniel@gmail.com","testeur QA","691683541","yaounde","image1","AAAAA");
        Employee employee2 = new Employee(2L,"carine","carine@gmail.com","Traductrice","690381176","CRADAT","image2","KLC");
        Employee employee3 = new Employee(3L,"roger","roger@gmail.com","devellopeur web","675668459","ETOA MEKI","image3","NR");

        when(employeeRepo.findAll()).thenReturn(List.of(employee1,employee2,employee3));

        List<Employee> employees = employee.listEmployee();

        assertThat(employees).hasSize(3).containsExactly(employee1,employee2,employee3);

    }

    @Test
    void ShouldGetEmployeeById(){
        Employee employee1 = new Employee(1L,"daniel","daniel@gmail.com","testeur QA","691683541","yaounde","image1","AAAAA");
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee1));

        Employee em = employee.employeeById(1L);

        assertThat(em).isEqualTo(employee1);

    }

    @Test
    void ShouldAddEmployee(){
        employeeDTO.setName("daniel");
        employeeDTO.setEmail("daniel@gmail.com");
        employeeDTO.setJobTitle("testeur QA");
        employeeDTO.setPhone("691683541");
        employeeDTO.setAddress("yaounde");
        employeeDTO.setImageUrl("image1");

        Employee employee2 = new Employee();
        employee2.setId(1L);
        employee2.setName(employeeDTO.getName());
        employee2.setEmail(employeeDTO.getEmail());
        employee2.setJobTitle(employeeDTO.getJobTitle());
        employee2.setPhone(employeeDTO.getPhone());
        employee2.setAddress(employeeDTO.getAddress());
        employee2.setImageUrl(employeeDTO.getImageUrl());
        employee2.setCodeEmployee(UUID.randomUUID().toString());

        when(employeeRepo.save(any(Employee.class))).thenReturn(employee2);

        Employee em = employee.add(employeeDTO);

        assertThat(em.getId()).isEqualTo(employee2.getId());
        assertThat(em.getName()).isEqualTo(employee2.getName());
        assertThat(em.getEmail()).isEqualTo(employee2.getEmail());
        assertThat(em.getJobTitle()).isEqualTo(employee2.getJobTitle());
        assertThat(em.getPhone()).isEqualTo(employee2.getPhone());
        assertThat(em.getAddress()).isEqualTo(employee2.getAddress());
        assertThat(em.getImageUrl()).isEqualTo(employee2.getImageUrl());
        assertThat(em.getCodeEmployee()).isEqualTo(employee2.getCodeEmployee());
    }

    @Test
    void ShouldUpdateEmployee(){

        employeeDTO.setName("daniel");
        employeeDTO.setEmail("daniel@gmail.com");
        employeeDTO.setJobTitle("testeur lead");
        employeeDTO.setPhone("691683541");
        employeeDTO.setAddress("yaounde");
        employeeDTO.setImageUrl("image1");

        Employee employee2 = new Employee();
        employee2.setId(1L);
        employee2.setName(employeeDTO.getName());
        employee2.setEmail(employeeDTO.getEmail());
        employee2.setJobTitle(employeeDTO.getJobTitle());
        employee2.setPhone(employeeDTO.getPhone());
        employee2.setAddress(employeeDTO.getAddress());
        employee2.setImageUrl(employeeDTO.getImageUrl());
        employee2.setCodeEmployee(UUID.randomUUID().toString());

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee2));
        when(employeeRepo.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employee em = employee.up(employeeDTO, 1L);

        assertThat(em.getId()).isEqualTo(employee2.getId());
        assertThat(em.getName()).isEqualTo(employee2.getName());
        assertThat(em.getEmail()).isEqualTo(employee2.getEmail());
        assertThat(em.getJobTitle()).isEqualTo(employee2.getJobTitle());
        assertThat(em.getPhone()).isEqualTo(employee2.getPhone());
        assertThat(em.getAddress()).isEqualTo(employee2.getAddress());
        assertThat(em.getImageUrl()).isEqualTo(employee2.getImageUrl());
        assertThat(em.getCodeEmployee()).isEqualTo(employee2.getCodeEmployee());
    }

    @Test
    void shouldDeletePerson(){
        // Configurer le mock pour simuler l'existence de l'employé
        when(employeeRepo.existsById(1L)).thenReturn(true);

        // Appeler la méthode à tester
        employee.delete(1L);

        // Vérifier que `existsById` a été appelé
        verify(employeeRepo).existsById(1L);

        // Vérifier que `deleteById` a été appelé
        verify(employeeRepo).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        // Configurer le mock pour retourner `false`
        when(employeeRepo.existsById(1L)).thenReturn(false);

        // Vérifier que l'exception est levée
        assertThrows(BadRequestException.class, () -> employee.delete(1L));

        // Vérifier que `deleteById` n'est jamais appelé
        verify(employeeRepo, never()).deleteById(1L);
    }

    @Test
    void shouldGenerateExcel() throws IOException {

        // Préparer les données mockées
        List<Employee> employees = List.of(
                new Employee(1L,"daniel","daniel@gmail.com","testeur QA","691683541","yaounde","image1","AAAAA"),
                new Employee(2L,"carine","carine@gmail.com","Traductrice","690381176","CRADAT","image2","KLC")
        );
        when(employeeRepo.findAll()).thenReturn(employees);

        // Mock de HttpServletResponse
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        // Appeler la méthode
        employee.generateExcel(response);

        // Vérifier les interactions
        verify(employeeRepo).findAll();
        verify(response).getOutputStream();

        // Charger et vérifier le contenu du fichier Excel
        HSSFWorkbook workbook = new HSSFWorkbook(new ByteArrayInputStream(outputStream.toByteArray()));
        HSSFSheet sheet = workbook.getSheet("Employee Liste");

        // Vérifier l'en-tête
        HSSFRow headerRow = sheet.getRow(0);
        assertEquals("Code", headerRow.getCell(0).getStringCellValue());
        assertEquals("Nom", headerRow.getCell(1).getStringCellValue());
        assertEquals("Poste", headerRow.getCell(2).getStringCellValue());
        assertEquals("Telephone", headerRow.getCell(3).getStringCellValue());
        assertEquals("adresse", headerRow.getCell(4).getStringCellValue());
        assertEquals("email", headerRow.getCell(5).getStringCellValue());
        assertEquals("Url", headerRow.getCell(6).getStringCellValue());

        // Vérifier les données des employés
        HSSFRow dataRow1 = sheet.getRow(1);
        assertEquals("AAAAA", dataRow1.getCell(0).getStringCellValue());
        assertEquals("daniel", dataRow1.getCell(1).getStringCellValue());
        assertEquals("testeur QA", dataRow1.getCell(2).getStringCellValue());
        assertEquals("691683541", dataRow1.getCell(3).getStringCellValue());
        assertEquals("yaounde", dataRow1.getCell(4).getStringCellValue());
        assertEquals("daniel@gmail.com", dataRow1.getCell(5).getStringCellValue());
        assertEquals("image1", dataRow1.getCell(6).getStringCellValue());

        HSSFRow dataRow2 = sheet.getRow(2);
        assertEquals("KLC", dataRow2.getCell(0).getStringCellValue());
        assertEquals("carine", dataRow2.getCell(1).getStringCellValue());
        assertEquals("Traductrice", dataRow2.getCell(2).getStringCellValue());
        assertEquals("690381176", dataRow2.getCell(3).getStringCellValue());
        assertEquals("CRADAT", dataRow2.getCell(4).getStringCellValue());
        assertEquals("carine@gmail.com", dataRow2.getCell(5).getStringCellValue());
        assertEquals("image2", dataRow2.getCell(6).getStringCellValue());

        workbook.close();

    }






}