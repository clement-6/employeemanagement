package tek.getarrays.employeemanagement.security.dto;


import lombok.Data;


@Data
public class UserDto {
    private Long id;
    private String userName;
    private String password;
//    private String passWordConfirm;
    private String role;
}
