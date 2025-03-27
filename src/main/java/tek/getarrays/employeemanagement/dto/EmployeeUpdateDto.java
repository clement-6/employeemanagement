package tek.getarrays.employeemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;

@Data
public class EmployeeUpdateDto {

    private long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = NAME_REQUIRED)
    @Size(max = 250)
    private String name;

    @ApiModelProperty(required = true)
    @Email(message = EMAIL_VALID)
    @NotBlank(message = EMAIL_REQUIRED)
    private String email;

    @Size(max = 200)
    private String jobTitle;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String address;

}
