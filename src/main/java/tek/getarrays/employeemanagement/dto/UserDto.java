package tek.getarrays.employeemanagement.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class UserDto {

    @ApiModelProperty(required = true)
    @NotBlank
    private String userName;
    @ApiModelProperty(required = true)
    @Email
    @NotBlank
    private String email;
    @ApiModelProperty(required = true)
    @NotBlank
    private String password;
    @Transient
    private String passWordConfirm;
    private String role;
}
