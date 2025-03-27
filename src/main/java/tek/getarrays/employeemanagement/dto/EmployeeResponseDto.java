package tek.getarrays.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;

@Data
public class EmployeeResponseDto {

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

    private String  matriculEmployee;

    @JsonFormat(pattern = "dd-MM-yyy")
    private Date createDate;

    @JsonFormat(pattern = "dd-MM-yyy")
    private Date updateDate;
}
