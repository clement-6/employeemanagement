package tek.getarrays.employeemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;

@Data
public class UserUpdateDto {

    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = NAME_REQUIRED)
    private String userName;

    @ApiModelProperty(required = true)
    @Email(message = EMAIL_VALID)
    @NotBlank(message = EMAIL_REQUIRED)
    private String email;

    private Set<String> roles;


}
