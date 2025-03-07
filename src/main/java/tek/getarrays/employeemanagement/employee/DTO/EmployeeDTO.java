package tek.getarrays.employeemanagement.employee.DTO;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String phone;
    private String address;
    private String imageUrl;
    private String codeEmployee;

}
