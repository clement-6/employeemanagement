package tek.getarrays.employeemanagement.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
;
import tek.getarrays.employeemanagement.Enum.UserStatus;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;


@Data
public class UserDto {

    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = NAME_REQUIRED)
    private String userName;

    @ApiModelProperty(required = true)
    @Email(message = EMAIL_VALID)
    @NotBlank(message = EMAIL_REQUIRED)
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = PASSWORD_REQUIRED)
    private String password;

    private String passWordConfirm;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private Set<String> roles;
}
