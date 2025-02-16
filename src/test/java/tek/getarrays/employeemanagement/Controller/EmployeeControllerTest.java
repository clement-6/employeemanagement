package tek.getarrays.employeemanagement.Controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tek.getarrays.employeemanagement.DTO.EmployeeDTO;
import tek.getarrays.employeemanagement.Entity.Employee;
import tek.getarrays.employeemanagement.Services.service.EmployeeService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeDTO employeeDTO;


    @Test
    void shouldReturnAllEmployee() throws Exception {
        Employee employee1 = new Employee(1L,"daniel","daniel@gmail.com","testeur QA","691683541","yaounde","image1","AAAAA");
        Employee employee2 = new Employee(2L,"carine","carine@gmail.com","Traductrice","690381176","CRADAT","image2","KLC");
        Employee employee3 = new Employee(3L,"roger","roger@gmail.com","devellopeur web","675668459","ETOA MEKI","image3","NR");

        when( employeeService.listEmployee()).thenReturn(List.of(employee1,employee2,employee3));

        mockMvc.perform(get("/employee/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("daniel"));


    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        Employee employee1 = new Employee(1L,"daniel","daniel@gmail.com","testeur QA","691683541","yaounde","image1","AAAAA");

        when(employeeService.employeeById(1L)).thenReturn(employee1);

        mockMvc.perform(get("/employee/By/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("daniel"))
                .andExpect(jsonPath("$.email").value("daniel@gmail.com"));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        employeeService.delete(1L);
        mockMvc.perform(delete("/employee/delete/1"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldAddEmployee() throws Exception {

        String json = """
                {
                    "name": "daniel",
                    "email": "daniel@gmail.com",
                    "jobTitle": "testeur QA",
                    "phone": "691683541",
                    "address": "yaounde",
                    "imageUrl": "https://bootdey.com/img/Content/avatar/avatar6.png"
                }
                
                """;

        employeeDTO.setName("daniel");
        employeeDTO.setEmail("daniel@gmail.com");
        employeeDTO.setJobTitle("testeur QA");
        employeeDTO.setPhone("691683541");
        employeeDTO.setAddress("yaounde");
        employeeDTO.setImageUrl("https://bootdey.com/img/Content/avatar/avatar6.png");

        Employee employee2 = new Employee(
                1L,
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getJobTitle(),
                employeeDTO.getPhone(),
                employeeDTO.getAddress(),
                employeeDTO.getImageUrl(),
                UUID.randomUUID().toString());

        when(employeeService.add(any(EmployeeDTO.class))).thenReturn(employee2);

        mockMvc.perform(post("/employee/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        String json = """
                {
                    "id": 1,
                    "name": "daniel",
                    "email": "daniel@gmail.com",
                    "jobTitle": "testeur QA",
                    "phone": "691683541",
                    "address": "Douala",
                    "imageUrl": "https://bootdey.com/img/Content/avatar/avatar6.png"
                }
                
                """;

        employeeDTO.setName("daniel");
        employeeDTO.setEmail("daniel@gmail.com");
        employeeDTO.setJobTitle("testeur QA");
        employeeDTO.setPhone("691683541");
        employeeDTO.setAddress("Douala");
        employeeDTO.setImageUrl("https://bootdey.com/img/Content/avatar/avatar6.png");



        Employee employee2 = new Employee(
                1L,
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getJobTitle(),
                employeeDTO.getPhone(),
                employeeDTO.getAddress(),
                employeeDTO.getImageUrl(),
                UUID.randomUUID().toString());

        when(employeeService.up(any(EmployeeDTO.class), eq(1L))).thenReturn(employee2);

        mockMvc.perform(put("/employee/up/employeeId/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }


}